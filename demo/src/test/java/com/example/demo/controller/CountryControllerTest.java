package com.example.demo.controller;

import com.example.demo.model.CountryModel;
import com.example.demo.repository.CountryRepository;
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
class CountryControllerTest {

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    void should_get_All_Countries() throws Exception {
        //given
        init();
        //when
        MvcResult mvcResult = mockMvc.perform(get("/country/allCountries"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        List<CountryModel> countryModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CountryModel>>() {
        });
        assertThat(countryModelList.get(countryModelList.size()-1).getCountryName()).isEqualTo("CountryTest2");
    }

    @Test
    @Transactional
    void should_get_Country_By_Id() throws Exception {
        //given
        CountryModel countryModel1 = new CountryModel();
        countryModel1.setCountryName("CountryModel1");
        countryModel1 = countryRepository.save(countryModel1);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/country/id/" + countryModel1.getId()))
                .andDo(print())
                .andReturn();
        CountryModel countryModelResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),CountryModel.class);
        assertThat(countryModelResult.getCountryName()).isEqualTo("CountryModel1");
    }

    @Test
    @Transactional
    void should_add_New_Continent() throws Exception{
        //given
        CountryModel countryModel1 = new CountryModel();
        countryModel1.setCountryName("CountryTest");
        //when
        mockMvc.perform(post("/country/addCountry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryModel1)))
                .andDo(print())
                .andExpect(jsonPath("$.countryName", Matchers.is("CountryTest")));
    }

    @Test
    @Transactional
    void deleteCountry() throws Exception {
        //given
        CountryModel countryModel1 = new CountryModel();
        countryModel1.setCountryName("CountryModel1");
        countryModel1 = countryRepository.save(countryModel1);

        List<CountryModel>countryModelListBeforeDelete = objectMapper.convertValue(countryRepository.findAll(), new TypeReference<List<CountryModel>>() {
        });
        assertThat(countryModelListBeforeDelete.get(countryModelListBeforeDelete.size()-1).getCountryName()).isEqualTo("CountryModel1");
        //when
        MvcResult mvcResult = mockMvc.perform(delete("/country/deleteCountry/" + countryModel1.getId()))
                .andDo(print())
                .andReturn();
        List<CountryModel>countryModelListAfterDelete = objectMapper.convertValue(countryRepository.findAll(), new TypeReference<List<CountryModel>>() {
        });
        assertThat(countryModelListBeforeDelete.size()).isEqualTo(countryModelListAfterDelete.size()-1);

    }

    @Test
    @Transactional
    void should_post_Country_By_Id() throws Exception {
        //given
        CountryModel countryModel1 = new CountryModel();
        countryModel1.setCountryName("CountryModel1");
        countryModel1 = countryRepository.save(countryModel1);

        CountryModel countryModelResult = new CountryModel();
        countryModelResult.setCountryName("CountryResult");
        //when
        mockMvc.perform(post("/country/id/" + countryModel1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryModelResult)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.countryName",Matchers.is("CountryResult")));

    }

    List<CountryModel> init() {
        CountryModel countryModel1 = new CountryModel();
        countryModel1.setCountryName("CountryTest1");
        countryRepository.save(countryModel1);
        CountryModel countryModel2 = new CountryModel();
        countryModel2.setCountryName("CountryTest2");
        countryRepository.save(countryModel2);
        List<CountryModel> countryModelList = new ArrayList<>();
        countryModelList.add(countryModel1);
        countryModelList.add(countryModel2);
        return countryModelList;
    }
}