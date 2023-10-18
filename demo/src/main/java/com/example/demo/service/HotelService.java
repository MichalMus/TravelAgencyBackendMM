package com.example.demo.service;

import com.example.demo.model.HotelModel;
import com.example.demo.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(final HotelRepository hotelRepository){
        this.hotelRepository=hotelRepository;
    }

    public List<HotelModel> getAllHotels(){
        return hotelRepository.findAll();
    }
    public void addNewHotel(HotelModel hotelModel){
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
