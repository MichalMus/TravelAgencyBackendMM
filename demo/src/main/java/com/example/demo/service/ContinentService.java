package com.example.demo.service;

import com.example.demo.model.ContinentModel;
import com.example.demo.repository.ContinentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ContinentService {
    private final ContinentRepository continentRepository;


    public ContinentService(final ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }

    public List<ContinentModel> getAllContinents() {
        return continentRepository.findAll();
    }

    public void addNewContinent(ContinentModel continentModel) {
        continentRepository.save(continentModel);
    }

    public void deleteContinentById(Long id){
        continentRepository.deleteById(id);
    }
    public Optional<ContinentModel> findContinent(Long id) {
        return continentRepository.findById(id);
    }

    public List<ContinentModel> AllContinents(){
        return continentRepository.findAll();
    }

    public Optional<ContinentModel> getContinentByName(String name){
        return continentRepository.findContinentByContinentName(name);
    }
}
