package com.example.demo.controller;

import com.example.demo.model.AirportModel;
import com.example.demo.repository.AirportRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc //dzięki temu tworzymy pseudo-klienta http
@WithMockUser //dodajemy aby ułatwić sobie życie, gdy aplikacja jest zabezpieczona tokenem
class AirportControllerTest {

    //UWAGA: nie skonfigurowaliśmy osobnej bazy danych do testów metod
    //więc może być tak, że coś zmieni się w bazie i testy się posypią
    //bo oczekiwane wyniki w testach nie będą się pokrywać z aktualnymi w bazie danych
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper; //potrzebne do drugiej metody testowania, aby jsona zmienić na obiekt

    @Autowired
    AirportRepository airportRepository; //wstrzykujemy aby dalej zapisać jakiś AirportModel i uniezależnić się od bazy danych


    @Test
    @Transactional
        //dodajemy aby tworząc w testach AirportModel nie zapisywać go w bazie po wielokroć
    void should_find_Airport_By_Id() throws Exception {
        //given
        AirportModel newAirportModel = new AirportModel();
//        newAirportModel.setId(1L);
        newAirportModel.setAirPortName("lotnisko");
        airportRepository.save(newAirportModel);


        //when - poniższe rozwiązanie dobrze się sprawdza gdy mamy mało rzeczy do sprawdzenia- zwraca bowiem Stringa
//        mockMvc.perform(get("/airport/id/1"))
//                .andDo(print())
//                .andExpect(status().is(200))
//                .andExpect(jsonPath("$.id", Matchers.is(1)))
//                .andExpect(jsonPath("$.airPortName", Matchers.is("lotniskoDzibutti")));
        //then


        //alternatywa dla powyższego rozwiązania
        //when
//        MvcResult mvcResult = mockMvc.perform(get("/airport/id/1")) //ta linijka albo ta poniżej - ta tutaj gdy nie dodawaliśmy w testach nowego modelu, tylko łączyliśmy się do bazy danych
        MvcResult mvcResult = mockMvc.perform(get("/airport/id/" + newAirportModel.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        //then
        AirportModel airportModel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AirportModel.class);
        assertThat(airportModel).isNotNull();
//        assertThat(airportModel.getId()).isEqualTo(1L);
        assertThat(airportModel.getAirPortName()).isEqualTo("lotnisko");

    }

    @Test
    @Transactional
    void should_get_All_Airports() throws Exception {
        //given
        init();
        //when
        MvcResult mvcResult = mockMvc.perform(get("/airport/allAirports"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        List<AirportModel> airportModelList = objectMapper.convertValue(airportRepository.findAll(), new TypeReference<List<AirportModel>>() {
        });
        assertThat(airportModelList.size()).isEqualTo(29);
        assertThat(airportModelList.get(24).getAirPortName()).isEqualTo("lotniskoSanFrancisko");
    }


    @Test
    @Transactional
    void should_add_New_Airport() throws Exception {
        //given
        AirportModel newAirportModel = new AirportModel();
        newAirportModel.setAirPortName("lotniskoTestowe");

        //when //then
        mockMvc.perform(post("/airport/addAirport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newAirportModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airPortName", Matchers.is("lotniskoTestowe")));
    }


    @Test
    @Transactional
    void should_delete_Airport() throws Exception {
        //given
        AirportModel newAirportModel = new AirportModel();
        newAirportModel.setAirPortName("lotniskoTestowe");
        airportRepository.save(newAirportModel);

        List<AirportModel> airportModelList = objectMapper.convertValue(airportRepository.findAll(), new TypeReference<List<AirportModel>>() {
        });
        assertThat(airportModelList.get(airportModelList.size() - 1).getAirPortName()).isEqualTo("lotniskoTestowe");

//        when
        MvcResult mvcResult = mockMvc.perform(delete("/airport/deleteAirport/" + (airportModelList.size() - 1)))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        //then
        List<AirportModel> airportModelList2 = objectMapper.convertValue(airportRepository.findAll(), new TypeReference<List<AirportModel>>() {
        });
        assertThat(airportModelList2.size()).isEqualTo(airportModelList.size() - 1);
    }

    @Test
    @Transactional
    void should_post_Airport_By_Id() throws Exception {
        //given
        AirportModel newAirportModel = new AirportModel();
        newAirportModel.setAirPortName("lotniskoTestowe");
        airportRepository.save(newAirportModel);

        AirportModel AirportResult = new AirportModel();
        AirportResult.setAirPortName("UluMulu");
        List<AirportModel> airportModelList = objectMapper.convertValue(airportRepository.findAll(), new TypeReference<List<AirportModel>>() {
        });
        assertThat(newAirportModel.getAirPortName()).isEqualTo(airportModelList.get(airportModelList.size() - 1).getAirPortName());

        mockMvc.perform(post("/airport/id/" + (airportModelList.size()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(AirportResult)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airPortName", Matchers.is("UluMulu")));
    }

    List<AirportModel> init() {
        AirportModel airport1 = new AirportModel();
        airport1.setAirPortName("lotniskoDzibutti");
        airportRepository.save(airport1);
        AirportModel airport2 = new AirportModel();
        airport2.setAirPortName("lotniskoAliSabieh");
        airportRepository.save(airport2);
        AirportModel airport3 = new AirportModel();
        airport3.setAirPortName("lotniskoTestoweInit");
        airportRepository.save(airport3);
        List<AirportModel> APList = new ArrayList<>();
        APList.add(airport1);
        APList.add(airport2);
        APList.add(airport3);
        return APList;
    }
}