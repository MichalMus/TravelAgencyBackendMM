package com.example.demo.controller;

import com.example.demo.model.CityModel;
import com.example.demo.model.HotelModel;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.HotelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class HotelControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    CityRepository cityRepository;

    @Test
    @Transactional
    void should_get_All_Hotels() throws Exception {
        //given
        init();
        //when
        MvcResult mvcResult = mockMvc.perform(get("/hotel/allHotels"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        List<HotelModel>hotelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<HotelModel>>() {
        });
        assertThat(hotelModelList.get(hotelModelList.size()-1).getHotelName()).isEqualTo("HotelTest2");
    }


    @Test
    @Transactional
    void should_find_Hotel_By_Id() throws Exception {
        //given
        HotelModel hotelModel1 = new HotelModel();
        hotelModel1.setHotelName("HotelTestowy");
        hotelModel1 = hotelRepository.save(hotelModel1);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/hotel/id/" + hotelModel1.getId()))
                .andDo(print())
                .andReturn();
        HotelModel hotelModelResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), HotelModel.class);
        assertThat(hotelModelResult.getHotelName()).isEqualTo("HotelTestowy");
    }

    @Test
    @Transactional
    void should_add_New_Hotel() throws Exception {
        //given
        HotelModel hotelModel1 = new HotelModel();
        hotelModel1.setHotelName("HotelTestowy");

        //when
        mockMvc.perform(post("/hotel/addHotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hotelModel1)))
                .andDo(print())
                .andExpect(jsonPath("$.hotelName", Matchers.is("HotelTestowy")));
    }

    @Test
    @Transactional
    void should_delete_Hotel() throws Exception {
        //given
        HotelModel hotelModel1 = new HotelModel();
        hotelModel1.setHotelName("HotelTestowy");
        hotelModel1 = hotelRepository.save(hotelModel1);

        List<HotelModel>hotelModelList = objectMapper.convertValue(hotelRepository.findAll(), new TypeReference<List<HotelModel>>() {
        });
        assertThat(hotelModelList.get(hotelModelList.size()-1).getHotelName()).isEqualTo("HotelTestowy");
        //when
        MvcResult mvcResult = mockMvc.perform(delete("/hotel/deleteHotel/" + hotelModel1.getId()))
                .andDo(print())
                .andReturn();
        List<HotelModel>hotelModelList2 = objectMapper.convertValue(hotelRepository.findAll(), new TypeReference<List<HotelModel>>() {
        });
        assertThat(hotelModelList2.size()).isEqualTo(hotelModelList.size()-1);
    }

    @Test
    @Transactional
    void should_post_Hotel_By_Id() throws Exception{
        //given
        HotelModel hotelModel1 = new HotelModel();
        hotelModel1.setHotelName("HotelTestowy");
        hotelModel1 = hotelRepository.save(hotelModel1);

        HotelModel hotelModelResult = new HotelModel();
        hotelModelResult.setHotelName("HotelResult");
        //when
        mockMvc.perform(post("/hotel/id/" + hotelModel1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hotelModelResult)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.hotelName",Matchers.is("HotelResult")));
    }

    @Test
    @Transactional
    void should_get_Hotels_In_City() throws Exception {
        //given
        init();
        String cityName = "Pekin";
        //when
        MvcResult mvcResult = mockMvc.perform(get("/hotel/" + cityName))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<HotelModel> hotelModelListInCity = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<HotelModel>>() {
        });
        assertThat(hotelModelListInCity).isNotNull();
        assertThat(hotelModelListInCity.get(hotelModelListInCity.size()-1).getHotelName()).isEqualTo("HotelTest2");

    }

    List<HotelModel> init() {
        CityModel cityModel1 = new CityModel();
        cityModel1.setCityName("Paryż");
        cityModel1 = cityRepository.save(cityModel1);
        CityModel cityModel2 = new CityModel();
        cityModel2.setCityName("Pekin");
        cityModel2 = cityRepository.save(cityModel2);

        HotelModel hotelModel1 = new HotelModel();
        hotelModel1.setHotelName("HotelTest1");
        hotelModel1.setCityModel(cityModel1);
        hotelRepository.save(hotelModel1);

        HotelModel hotelModel2 = new HotelModel();
        hotelModel2.setHotelName("HotelTest2");
        hotelModel2.setCityModel(cityModel2);
        hotelRepository.save(hotelModel2);

        List<HotelModel> hotelModelList = new ArrayList<>();
        hotelModelList.add(hotelModel1);
        hotelModelList.add(hotelModel1);
        return hotelModelList;
    }
}