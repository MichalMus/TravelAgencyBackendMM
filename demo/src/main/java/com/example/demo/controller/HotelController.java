package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;



    @GetMapping("/{city}")
    public ResponseEntity<List<HotelModel>> getHotelsInCity(@PathVariable("city") String cityName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelService.findAllHotelsInCity(cityName));
    }

    @GetMapping("/allHotels")
    public ResponseEntity<List<HotelModel>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<HotelModel>> findHotelById(@PathVariable("id") Long id) {
        final Optional<HotelModel> hotelModel = hotelService.findHotelById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelModel);
    }

    @PostMapping("/addHotel")
    public ResponseEntity addNewHotel(@RequestBody HotelModel hotelModel) {
        hotelService.addNewHotel(hotelModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelModel);
    }


    @DeleteMapping("/deleteHotel/{id}")
    public ResponseEntity deleteHotel(@PathVariable("id") Long id) {
        try {
            hotelService.deleteHotelById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) { //spradzić czy dobrze zapisane tre wyjątki
            e.printStackTrace();
//            throw new TravelNotFoundException(id);  tutaj zmienić stworzyć nową klasę wyjątku
        }
        return null;
    }

    @PostMapping("/id/{id}")
    public ResponseEntity<HotelModel> postHotelById(@PathVariable("id") Long id, @RequestBody HotelModel hotelModel) {
        hotelService.addNewHotel(hotelModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hotelModel);
    }

    @GetMapping("/sort")
    public ResponseEntity<HotelResponse> getAllWithFilterPageAndSort(@RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false) Integer size,
                                                                     @RequestParam(required = false) String sort,
                                                                     @RequestParam(required = false) String column,
                                                                     @RequestParam(required = false) String cityName){

        Sort.Direction direction = Sort.Direction.fromString(sort);
        if (page == null){
            page = 0;
        }
        if (size == null){
            size = 5;
        }
        Integer pageNumber = page >= 0 ? page : 0;
        Integer sizeNumber = size > 0 ? size : 5;
        int allHotels = hotelService.getAllHotels().size();
        HotelResponse hotelResponse;

        if( cityName == null || cityName.equals("")){
            hotelResponse = new HotelResponse(hotelService.findAllWithSort(pageNumber,sizeNumber,direction,column),allHotels);
        } else{
            hotelResponse = new HotelResponse(hotelService.findHotelsByCityWithPage(cityName    ,pageNumber,sizeNumber,direction,column),allHotels);
        }

//        hotelResponse = new HotelResponse(hotelService.findHotelsByCityWithPage(surname,
//                pageNumber,sizeNumber,direction,column),allHotels);
        return ResponseEntity.status(HttpStatus.OK).body(hotelResponse);

    }


}

