package com.example.demo.controller;

import com.example.demo.model.PersonsIdModel;
import com.example.demo.repository.PersonsIdRepository;
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
@WithMockUser
@AutoConfigureMockMvc
class PersonsIdControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PersonsIdRepository personsIdRepository;

    @Test
    @Transactional
    void should_get_All_Persons() throws Exception {
        //given
        init();
        //when
        MvcResult mvcResult = mockMvc.perform(get("/personsid/allPersons"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        List<PersonsIdModel> personsIdModelList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PersonsIdModel>>() {
        });
        assertThat(personsIdModelList.get(personsIdModelList.size() - 1).getPersonSurname()).isEqualTo("Nowak");
    }

    @Test
    @Transactional
    void should_get_Person_By_Id() throws Exception {
        //given
        PersonsIdModel personsIdModel1 = new PersonsIdModel();
        personsIdModel1.setPersonSurname("Bykowski");
        personsIdModel1 = personsIdRepository.save(personsIdModel1);
        //when
        MvcResult mvcResult = mockMvc.perform(get("/personsid/id/" + personsIdModel1.getId()))
                .andDo(print())
                .andReturn();
        PersonsIdModel personsIdModelResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonsIdModel.class);
        assertThat(personsIdModelResult.getPersonSurname()).isEqualTo("Bykowski");
    }

    @Test
    @Transactional
    void should_add_New_Person() throws Exception {
        //given
        PersonsIdModel personsIdModel1 = new PersonsIdModel();
        personsIdModel1.setPersonSurname("Bykowski");
        //when
        mockMvc.perform(post("/personsid/addPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personsIdModel1)))
                .andDo(print())
                .andExpect(jsonPath("$.personSurname", Matchers.is("Bykowski")));
    }

    @Test
    @Transactional
    void should_delete_Person() throws Exception {
        //given
        PersonsIdModel personsIdModel1 = new PersonsIdModel();
        personsIdModel1.setPersonSurname("Nowak");
        personsIdModel1 = personsIdRepository.save(personsIdModel1);
        //when
        List<PersonsIdModel> personsIdModelList = objectMapper.convertValue(personsIdRepository.findAll(), new TypeReference<List<PersonsIdModel>>() {
        });
        assertThat(personsIdModelList.get(personsIdModelList.size() - 1).getPersonSurname()).isEqualTo("Nowak");

        MvcResult mvcResult = mockMvc.perform(delete("/personsid/deletePerson/" + personsIdModel1.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<PersonsIdModel> personsIdModelListResult = objectMapper.convertValue(personsIdRepository.findAll(), new TypeReference<List<PersonsIdModel>>() {
        });
        assertThat(personsIdModelListResult.size()).isEqualTo(personsIdModelList.size() - 1);
    }

    @Test
    @Transactional
    void should_post_Person_By_Id() throws Exception {
        //given
        PersonsIdModel personsIdModel1 = new PersonsIdModel();
        personsIdModel1.setPersonSurname("Kowalski");
        personsIdModel1 = personsIdRepository.save(personsIdModel1);

        PersonsIdModel personsIdModel2 = new PersonsIdModel();
        personsIdModel2.setPersonSurname("Bykowski");
        //when
        mockMvc.perform(post("/personsid/id/" + personsIdModel1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personsIdModel2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.personSurname", Matchers.is("Bykowski")));
    }

    @Test
    @Transactional
    void should_get_All_Persons_By_Surname() throws Exception{
        //given
        init();
        String surname = "Kowalski";
        //when
        MvcResult mvcResult = mockMvc.perform(get("/personsid/surname/" + surname))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        List<PersonsIdModel> personsIdModelListResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PersonsIdModel>>() {
        });
        assertThat(personsIdModelListResult).isNotNull();
        assertThat(personsIdModelListResult.get(0).getPersonSurname()).isEqualTo(surname);

    }

    public List<PersonsIdModel> init() {
        PersonsIdModel personsIdModel1 = new PersonsIdModel();
        personsIdModel1.setPersonSurname("Bykowski");
        personsIdModel1 = personsIdRepository.save(personsIdModel1);
        PersonsIdModel personsIdModel2 = new PersonsIdModel();
        personsIdModel2.setPersonSurname("Kowalski");
        personsIdModel2 = personsIdRepository.save(personsIdModel2);
        PersonsIdModel personsIdModel3 = new PersonsIdModel();
        personsIdModel3.setPersonSurname("Nowak");
        personsIdModel3 = personsIdRepository.save(personsIdModel3);

        List<PersonsIdModel> personsIdModelList = new ArrayList<>();
        personsIdModelList.add(personsIdModel1);
        personsIdModelList.add(personsIdModel2);
        personsIdModelList.add(personsIdModel3);
        return personsIdModelList;
    }
}