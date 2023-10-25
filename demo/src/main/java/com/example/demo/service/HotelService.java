package com.example.demo.service;

import com.example.demo.model.CityModel;
import com.example.demo.model.ContinentModel;
import com.example.demo.model.CountryModel;
import com.example.demo.model.HotelModel;
import com.example.demo.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private ContinentService continentService;
    private CountryService countryService;
    private CityService cityService;

//    public HotelService(final HotelRepository hotelRepository){
//        this.hotelRepository=hotelRepository;
//    }

    public HotelService(HotelRepository hotelRepository, ContinentService continentService, CountryService countryService, CityService cityService) {
        this.hotelRepository = hotelRepository;
        this.continentService = continentService;
        this.countryService = countryService;
        this.cityService = cityService;
    }

    public List<HotelModel> getAllHotels(){
        return hotelRepository.findAll();
    }
    public void addNewHotel(HotelModel hotelModel){
        ContinentModel continent;
        CountryModel country;
        CityModel city;

        if(continentService.getContinentByName(hotelModel.getCityModel().getCountryModel().getContinentModel().getContinentName()).isEmpty()){
           continent = new ContinentModel();
            continent.setContinentName(hotelModel.getCityModel().getCountryModel().getContinentModel().getContinentName());
            continentService.addNewContinent(continent);
        } else {
            continent = continentService.getContinentByName(hotelModel.getCityModel().getCountryModel().getContinentModel().getContinentName()).orElseThrow();
        }
        if(countryService.getCountryByName(hotelModel.getCityModel().getCountryModel().getCountryName()).isEmpty()){
            country = new CountryModel();

            country.setCountryName(hotelModel.getCityModel().getCountryModel().getCountryName());
            country.setContinentModel(continent);
            countryService.addNewCountry(country);
        }else {
            country = countryService.getCountryByName(hotelModel.getCityModel().getCountryModel().getCountryName()).orElseThrow();
        }
        if(cityService.getCityByName(hotelModel.getCityModel().getCityName()).isEmpty()){
            city = new CityModel();

            city.setCityName(hotelModel.getCityModel().getCityName());
            city.setCountryModel(country);
            cityService.addNewCity(city);
        }else {
            city = cityService.getCityByName(hotelModel.getCityModel().getCityName()).orElseThrow();
        }

            hotelModel.setCityModel(city);
            hotelRepository.save(hotelModel);
    }


    public void deleteHotelById(Long id){
        hotelRepository.deleteById(id);
    }

    public Optional<HotelModel> findHotelById(Long id){
        return hotelRepository.findById(id);
    }

    public List<HotelModel> findAllHotelsInCity(String cityName){
        return hotelRepository.findAllByCityModel_CityName(cityName);
    }

    public List<HotelModel> findAllWithSort( int page, int size, Sort.Direction sort, String column){
        return hotelRepository.findAllHotels(PageRequest.of(page,size, Sort.by(sort,column)));
    }

    public List<HotelModel> findHotelsByCityWithPage( String name, int page, int size, Sort.Direction sort, String column){
        return hotelRepository.findHotelsByCity(name, PageRequest.of(page,size, Sort.by(sort,column)));
    }
}
