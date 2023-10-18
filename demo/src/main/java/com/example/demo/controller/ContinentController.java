package com.example.demo.controller;

import com.example.demo.model.ContinentModel;
import com.example.demo.service.ContinentService;
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
@RequestMapping("/continent")
public class ContinentController {

    private final ContinentService continentService;
    @GetMapping("/allContinents")
    public ResponseEntity<List<ContinentModel>> getAllContinent() {
        return ResponseEntity.ok(continentService.AllContinents());

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<ContinentModel>> getContinentById(@PathVariable("id") Long id) {
        final Optional<ContinentModel> continentModel = continentService.findContinent(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(continentModel);
    }

    @PostMapping("/addContinent")
    public ResponseEntity addNewContinent(@RequestBody ContinentModel continentModel) {
        continentService.addNewContinent(continentModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(continentModel);
    }

    @DeleteMapping("/deleteContinent/{id}")
    public ResponseEntity deleteContinent(@PathVariable("id") Long id) {
        try {
            continentService.deleteContinentById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) { //spradzić czy dobrze zapisane tre wyjątki
            e.printStackTrace();
//            throw new TravelNotFoundException(id);  tutaj zmienić stworzyć nową klasę wyjątku
        }
        return null;
    }



    @PostMapping("/id/{id}")
    public ResponseEntity<ContinentModel> postContinentById(@PathVariable("id") Long id, @RequestBody ContinentModel continentModel) {
        continentService.addNewContinent(continentModel);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(continentModel);
    }


}
