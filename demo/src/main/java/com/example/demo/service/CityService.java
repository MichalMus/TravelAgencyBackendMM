package com.example.demo.service;

import com.example.demo.model.CityModel;
import com.example.demo.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityModel> getAllCities() {
        return cityRepository.findAll();
    }

    public void addNewCity(CityModel cityModel) {
        cityRepository.save(cityModel);
    }

    public void deleteCityById(Long id){
        cityRepository.deleteById(id);
    }
    public Optional<CityModel> getCityById(Long id){
        return cityRepository.findById(id);
    }
}
