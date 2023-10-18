package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.AirportRepository;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.TravelRepository;
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
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class TravelControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TravelRepository travelRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    HotelRepository hotelRepository;


    @Test
    @Transactional
    void should_get_All_Travels() throws Exception {
        //given
        init();
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/promotion"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<TravelModel> travelModels = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModels.get(travelModels.size() - 1).getAdultsNumber()).isEqualTo(init().get(init().size() - 2).getAdultsNumber());
        assertThat(travelModels).isNotNull();
    }


    @Test
    @Transactional
    void should_get_All_Travels_Near_This_Date() throws Exception {
        //given
        init();
        DateDTO dateDTO = new DateDTO();
        Date date1 = new Date(1998 - 12 - 25);
        Date date2 = new Date(1998 - 12 - 01);
        dateDTO.setDate1(date1);
        dateDTO.setDate2(date2);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/near/between")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dateDTO)))
                .andReturn();
        //then
        List<TravelModel> travelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelList.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void should_get_All_By_Country() throws Exception {
        //given
        init();
        String countryName = "UluMulu";
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/country/" + countryName))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        List<TravelModel> travelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelList).isNotNull();
        assertThat(travelModelList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void should_get_All_By_Airport() throws Exception {
        //given
        init();
        String airportName = "AUM";
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/airport/" + airportName))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        List<TravelModel> travelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelList).isNotNull();
        assertThat(travelModelList.size()).isEqualTo(2);
    }


    @Test
    @Transactional
    void should_get_All_By_Hotel() throws Exception {
        //given
        init();
        String hotelName = "HotelUluMulu";
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/hotel/" + hotelName))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        List<TravelModel> travelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelList).isNotNull();
        assertThat(travelModelList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void should_get_All_Travels_Near_This_End_Date() throws Exception {
        //given
        init();
        DateDTO dateDTO = new DateDTO();
        Date date2 = new Date(1998 - 12 - 20);
        Date date1 = new Date(1998 - 12 - 30);
        dateDTO.setDate1(date1);
        dateDTO.setDate2(date2);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/endDate/between")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dateDTO)))
                .andReturn();
        //then
        List<TravelModel> travelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelList.size()).isEqualTo(1);
    }


    @Test
    @Transactional
    void should_get_All_Travels_By_Hotel_Stars() throws Exception {
        //given
        init();
        byte stars = 6;
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/hotelStars/" + stars))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        List<TravelModel> travelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelList).isNotNull();
        assertThat(travelModelList.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void should_get_Tr_By_Id() throws Exception {
        //given
        TravelModel travelModel = new TravelModel();
        travelModel = travelRepository.save(travelModel);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/id/" + travelModel.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        TravelModel travelModelResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TravelModel.class);
        assertThat(travelModelResult.getId()).isEqualTo(travelModel.getId());

    }

    @Test
    @Transactional
    void getAllTravelsInThisDate() throws Exception {
        //given
        TravelModel travelModel = new TravelModel();
        travelModel.setStartDate(new Date(1997 - 07 - 07));
        travelModel = travelRepository.save(travelModel);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/travel/startDate/1997-07-07"))
                .andDo(print())
                .andReturn();
        //then
        List<TravelModel> travelModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelList).isNotNull();
        assertThat(travelModelList.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void should_add_New_Travel() throws Exception{
        //given
        TravelModel travelModel = new TravelModel();
        travelModel.setNumberOfDays((byte) 123);
        //when
        mockMvc.perform(post("/travel/addTravel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(travelModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.numberOfDays", Matchers.is(123)));

    }

    @Test
    @Transactional
    void should_delete_Travel_By_Id() throws Exception {
        //given
        TravelModel travelModel = new TravelModel();
        travelModel = travelRepository.save(travelModel);
        //when
        List<TravelModel> travelModelList = objectMapper.convertValue(travelRepository.findAll(), new TypeReference<List<TravelModel>>() {
        });
        MvcResult mvcResult = mockMvc.perform(delete("/travel/deleteTravel/" + (travelModel.getId())))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        List<TravelModel> travelModelsResult = objectMapper.convertValue(travelRepository.findAll(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModelsResult.size()).isEqualTo(travelModelList.size() - 1);
    }

    @Test
    @Transactional
    void should_post_Tr_By_Id() throws Exception {
        //given
        TravelModel travelModel1 = new TravelModel();
        travelModel1.setAdultPrice(111);
        travelModel1 = travelRepository.save(travelModel1);

        TravelModel travelModel2 = new TravelModel();
        travelModel2.setAdultPrice(222);
        //when
        List<TravelModel> travelModels = objectMapper.convertValue(travelRepository.findAll(), new TypeReference<List<TravelModel>>() {
        });
        assertThat(travelModels.get(travelModels.size()-1).getAdultPrice()).isEqualTo(111);
        mockMvc.perform(post("/travel/id/" + travelModel1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(travelModel2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.adultPrice", Matchers.is(222)));

    }

    List<TravelModel> init() {
        CountryModel countryModel1 = new CountryModel();
        countryModel1.setCountryName("UluMulu");
        countryModel1 = countryRepository.save(countryModel1);
        Date start = new Date(2020 - 01 - 05);
        Date end = new Date(2020 - 01 - 15);
        AirportModel airportModel = new AirportModel();
        airportModel.setAirPortName("AUM");
        airportModel = airportRepository.save(airportModel);
        HotelModel hotelModel = new HotelModel();
        hotelModel.setHotelName("HotelUluMulu");
        hotelModel.setStarsNumber((byte) 6);
        hotelModel = hotelRepository.save(hotelModel);

        TravelModel travelModel1 = new TravelModel();
        travelModel1.setChildPrice(10);
        travelModel1.setAdultPrice(100);
        travelModel1.setChildrenNumber((byte) 1);
        travelModel1.setAdultsNumber((byte) 1);
        travelModel1.setNumberOfDays((byte) 10);
        travelModel1.setPromotion(true);
        travelModel1.setCountryModel(countryModel1);
        travelModel1.setStartDate(start);
        travelModel1.setEndDate(end);
        travelModel1.setStart(airportModel);
        travelModel1.setHotelModel(hotelModel);
        travelModel1 = travelRepository.save(travelModel1);

        TravelModel travelModel2 = new TravelModel();
        travelModel2.setChildPrice(20);
        travelModel2.setAdultPrice(200);
        travelModel2.setChildrenNumber((byte) 2);
        travelModel2.setAdultsNumber((byte) 2);
        travelModel2.setNumberOfDays((byte) 20);
        travelModel2.setPromotion(false);
        Date start2 = new Date(1998 - 12 - 20);
        Date end2 = new Date(1998 - 12 - 28);
        travelModel2.setStartDate(start2);
        travelModel2.setEndDate(end2);
        travelModel2.setCountryModel(countryModel1);
        travelModel2.setStart(airportModel);
        travelModel2.setHotelModel(hotelModel);

        travelModel2 = travelRepository.save(travelModel2);

        List<TravelModel> travelModelList = new ArrayList<>();
        travelModelList.add(travelModel1);
        travelModelList.add(travelModel2);
        return travelModelList;
    }
}