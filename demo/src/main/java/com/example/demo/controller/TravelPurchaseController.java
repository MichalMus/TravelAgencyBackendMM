package com.example.demo.controller;

import com.example.demo.model.TravelPurchaseModel;
import com.example.demo.service.TravelPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/travelpurchase")
public class TravelPurchaseController {
    private final TravelPurchaseService travelPurchaseService;

    @GetMapping("/allTravelPurchase")
    public ResponseEntity<List<TravelPurchaseModel>> getAllTravelPurchase() {
        return ResponseEntity.ok(travelPurchaseService.getAllTravelPurchaseModels());

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<TravelPurchaseModel>> getTravelPurchaseById(@PathVariable("id") Long id) {
        final Optional<TravelPurchaseModel> travelPurchaseModel = travelPurchaseService.findTravelPurchaseById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travelPurchaseModel);
    }

    @GetMapping("/travelModelId/{id}")
    public ResponseEntity<List<TravelPurchaseModel>> getTravelPurchaseByTravelModel(@PathVariable("id") Long id) {
        final List<TravelPurchaseModel> travelPurchaseModel = travelPurchaseService.getAllTravelPurchaseModelsByTravelModel(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travelPurchaseModel);
    }

    @PostMapping("/addTravelPurchase")
    public ResponseEntity addNewTravelPurchase(@RequestBody TravelPurchaseModel travelPurchaseModel) {
        travelPurchaseService.addNewTravelPurchaseModel(travelPurchaseModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/deleteTravelPurchase/{id}")
    public ResponseEntity deleteTravelPurchase(@PathVariable("id") Long id) {
        try {
            travelPurchaseService.deleteTravelPurchaseModelById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) { //spradzić czy dobrze zapisane tre wyjątki
            e.printStackTrace();
//            throw new TravelNotFoundException(id);  tutaj zmienić stworzyć nową klasę wyjątku
        }
        return null;
    }


    @PostMapping("/id/{id}")
    public ResponseEntity<TravelPurchaseModel> postTravelPurchaseById(@PathVariable("id") Long id, @RequestBody TravelPurchaseModel travelPurchaseModel) {
        travelPurchaseService.changeTravelPurchaseModel(id,travelPurchaseModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

//    @PostMapping("/id")
//    public ResponseEntity<TravelPurchaseModel> postTravelPurchaseById(UriComponentsBuilder builder, @RequestBody TravelPurchaseModel travelPurchaseModel) {
//        Long travelPurchaseId = travelPurchaseModel.getId();
//        UriComponents uriComponents = builder.path("/{id}").buildAndExpand(travelPurchaseId);
//        travelPurchaseService.addNewTravelPurchaseModel(travelPurchaseModel);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .build();
//    }

    @GetMapping("/price/{id}")
    public ResponseEntity<Double> getTravelPriceForAll(@PathVariable("id") Long id){
        return ResponseEntity.ok(travelPurchaseService.getTravelPrice(id));
    }

    @PostMapping("/changeNumberOfPlaces/{id}")
    public ResponseEntity postTravelNumberOfPlaces(@PathVariable("id") Long id) {
        travelPurchaseService.changeNumberOfPlacesInTravel(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

}

