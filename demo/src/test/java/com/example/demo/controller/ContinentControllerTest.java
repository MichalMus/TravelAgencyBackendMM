package com.example.demo.controller;

import com.example.demo.model.ContinentModel;
import com.example.demo.repository.ContinentRepository;
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
class ContinentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ContinentRepository continentRepository;



    @Test
    @Transactional
    void should_get_All_Continents() throws Exception {
        //given
        init();
        //when
        MvcResult mvcResult = mockMvc.perform(get("/continent/allContinents"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        List<ContinentModel>continentModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ContinentModel>>() {
        });
        assertThat(continentModelList.get(continentModelList.size()-1).getContinentName()).isEqualTo("ContinentTest2");
    }

    @Test
    @Transactional
    void should_get_Continent_By_Id() throws Exception {
        //given
        ContinentModel continentModel1 = new ContinentModel();
        continentModel1.setContinentName("KontynentTestowy");
        continentModel1 = continentRepository.save(continentModel1);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/continent/id/" + continentModel1.getId()))
                .andDo(print())
                .andReturn();
        ContinentModel continentModelResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContinentModel.class);
        assertThat(continentModelResult.getContinentName()).isEqualTo("KontynentTestowy");

    }

    @Test
    @Transactional
    void should_add_New_Continent() throws Exception {
        //given
        ContinentModel continentModel1 = new ContinentModel();
        continentModel1.setContinentName("KontynentTestowy");
        //when
        mockMvc.perform(post("/continent/addContinent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(continentModel1)))
                .andDo(print())
                .andExpect(jsonPath("$.continentName", Matchers.is("KontynentTestowy")));

    }

    @Test
    @Transactional
    void should_delete_Continent() throws Exception {
        //given
        ContinentModel continentModel1 = new ContinentModel();
        continentModel1.setContinentName("KontynentTestowy");
        continentModel1 = continentRepository.save(continentModel1);
        //when
        List<ContinentModel>continentsList1 = objectMapper.convertValue(continentRepository.findAll(), new TypeReference<List<ContinentModel>>() {
        });
        assertThat(continentsList1.get(continentsList1.size()-1).getContinentName()).isEqualTo("KontynentTestowy");

        MvcResult mvcResult = mockMvc.perform(delete("/continent/deleteContinent/" + continentModel1.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<ContinentModel>continentsList2 = objectMapper.convertValue(continentRepository.findAll(), new TypeReference<List<ContinentModel>>() {
        });
        assertThat(continentsList2.size()).isEqualTo(continentsList1.size()-1);
    }

    @Test
    @Transactional
    void should_post_Continent_By_Id() throws Exception {
        //given
        ContinentModel continentModel1 = new ContinentModel();
        continentModel1.setContinentName("KontynentTestowy");
        continentModel1 = continentRepository.save(continentModel1);

        ContinentModel continentModelResult = new ContinentModel();
        continentModelResult.setContinentName("ContinentResult");
        //when
        mockMvc.perform(post("/continent/id/" + continentModel1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(continentModelResult)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.continentName",Matchers.is("ContinentResult")));

    }

    List<ContinentModel> init() {
        ContinentModel continentModel1 = new ContinentModel();
        continentModel1.setContinentName("ContinentTest1");
        continentRepository.save(continentModel1);
        ContinentModel continentModel2 = new ContinentModel();
        continentModel2.setContinentName("ContinentTest2");
        continentRepository.save(continentModel2);
        List<ContinentModel> continentModelList = new ArrayList<>();
        continentModelList.add(continentModel1);
        continentModelList.add(continentModel2);
        return continentModelList;
    }
}