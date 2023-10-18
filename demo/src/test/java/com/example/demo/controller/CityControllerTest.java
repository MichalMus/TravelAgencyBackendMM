package com.example.demo.controller;

import com.example.demo.model.AirportModel;
import com.example.demo.model.CityModel;
import com.example.demo.repository.CityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.Mysqlx;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.ServerResponse.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class CityControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CityRepository cityRepository;

    @Test
    @Transactional
    void should_get_All_Cities() throws Exception {
//        given
        init();
//        when
        MvcResult mvcResult = mockMvc.perform(get("/city/allCities"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<CityModel> cityModels = objectMapper.convertValue(cityRepository.findAll(), new TypeReference<List<CityModel>>() {
        });
        assertThat(cityModels.get((cityModels.size() - 2)).getCityName()).isEqualTo("CityTest1");
        assertThat(cityModels).isNotNull();

    }

    @Test
    @Transactional
    void should_get_City_By_Id() throws Exception {
        //given
        CityModel cityModelTest = new CityModel();
        cityModelTest.setCityName("MiastoTestowe");
        cityModelTest = cityRepository.save(cityModelTest);
        List<CityModel> cities = objectMapper.convertValue(cityRepository.findAll(), new TypeReference<List<CityModel>>() {
        });
        assertThat(cities.get(cities.size()-1).getCityName()).isEqualTo("MiastoTestowe");


        MvcResult mvcResult = mockMvc.perform(get("/city/id/" + cityModelTest.getId()))
                .andDo(print())
                .andReturn();
        CityModel cityModelResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CityModel.class);
        assertThat(cityModelResult.getCityName()).isEqualTo("MiastoTestowe");


    }

    @Test
    @Transactional
    void should_add_New_City() throws Exception {
        //given
        CityModel cityModelTest = new CityModel();
        cityModelTest.setCityName("MiastoTestowe");
        //when
        mockMvc.perform(post("/city/addCity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cityModelTest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cityName", Matchers.is("MiastoTestowe")));

    }

    @Test
    @Transactional
    void should_delete_City() throws Exception {
        //given
        CityModel cityModelTest = new CityModel();
        cityModelTest.setCityName("MiastoTestowe");
        cityRepository.save(cityModelTest);
        //when
        List<CityModel> cityModels = objectMapper.convertValue(cityRepository.findAll(), new TypeReference<List<CityModel>>() {
        });
        assertThat(cityModels.get((cityModels.size() - 1)).getCityName()).isEqualTo("MiastoTestowe");
        MvcResult mvcResult = mockMvc.perform(delete("/city/deleteCity/" + (cityModels.size() - 1)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        List<CityModel> cityModels2 = objectMapper.convertValue(cityRepository.findAll(), new TypeReference<List<CityModel>>() {
        });
        assertThat(cityModels2.size()).isEqualTo(cityModels.size() - 1);

    }

    @Test
    @Transactional
    void should_post_City_By_Id() throws Exception {
        //given
        CityModel cityModelTest = new CityModel();
        cityModelTest.setCityName("MiastoTestowe");
        cityRepository.save(cityModelTest);

        CityModel cityModelResult = new CityModel();
        cityModelResult.setCityName("Ulumulu");
        //when
        List<CityModel> cityModels = objectMapper.convertValue(cityRepository.findAll(), new TypeReference<List<CityModel>>() {
        });
        assertThat(cityModelTest.getCityName()).isEqualTo(cityModels.get(cityModels.size() - 1).getCityName());

        mockMvc.perform(post("/city/id/" + (cityModels.size()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cityModelResult)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cityName", Matchers.is("Ulumulu")));

    }

    List<CityModel> init() {
        CityModel cityModel1 = new CityModel();
        cityModel1.setCityName("CityTest1");
        cityRepository.save(cityModel1);
        CityModel cityModel2 = new CityModel();
        cityModel2.setCityName("CityTest2");
        cityRepository.save(cityModel2);
        List<CityModel> cityModelList = new ArrayList<>();
        cityModelList.add(cityModel1);
        cityModelList.add(cityModel2);
        return cityModelList;
    }
}