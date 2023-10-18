package com.example.demo.controller;

import com.example.demo.model.AirportModel;
import com.example.demo.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/airport")
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/{city}")
    public ResponseEntity<List<AirportModel>> getAirportsInCity(@PathVariable("city") String cityName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportService.findAirportByCity2(cityName));
    }

    @GetMapping("/allAirports")
    public ResponseEntity<List<AirportModel>> getAllAirports() {
        return ResponseEntity.ok(airportService.getAllAirports());

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<AirportModel>> findAirportById(@PathVariable("id") Long id) {
        final Optional<AirportModel> airportModel = airportService.getAirportById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportModel);
    }

    @PostMapping("/addAirport")
    public ResponseEntity addNewAirport(@RequestBody AirportModel airportModel) {
        airportService.addAirport(airportModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportModel);
    }

    @DeleteMapping("/deleteAirport/{id}")
    public ResponseEntity deleteAirport(@PathVariable("id") Long id) {
        try {
            airportService.deleteAirport(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) { //spradzić czy dobrze zapisane tre wyjątki
            e.printStackTrace();
//            throw new TravelNotFoundException(id);  tutaj zmienić stworzyć nową klasę wyjątku
        }
        return null;
    }


    @PostMapping("/id/{id}")
    public ResponseEntity<AirportModel> postAirportById(@PathVariable("id") Long id, @RequestBody AirportModel airportModel) {
        airportService.addAirport(airportModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(airportModel);
    }


}