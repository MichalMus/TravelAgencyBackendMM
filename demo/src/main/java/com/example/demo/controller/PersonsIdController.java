package com.example.demo.controller;

import com.example.demo.model.ClientsResponse;
import com.example.demo.model.PersonsIdModel;
import com.example.demo.service.PersonsIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/personsid")
public class PersonsIdController {

    private final PersonsIdService personsIdService;

    @GetMapping("/allPersons")
    public ResponseEntity<List<PersonsIdModel>> getAllPersons() {
        return ResponseEntity.ok(personsIdService.getAllPersonsIdList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<PersonsIdModel>> getPersonById(@PathVariable("id") Long id) {
        final Optional<PersonsIdModel> personsIdModel = personsIdService.findPersonById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personsIdModel);
    }

    @PostMapping("/addPerson")
    public ResponseEntity addNewPerson(@RequestBody PersonsIdModel personsIdModel) {
        personsIdService.addNewPersonsId(personsIdModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personsIdModel);
    }

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id) {
        try {
            personsIdService.deletePersonsIdById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) { //spradzić czy dobrze zapisane tre wyjątki
            e.printStackTrace();
//            throw new TravelNotFoundException(id);  tutaj zmienić stworzyć nową klasę wyjątku
        }
        return null;
    }


    @PostMapping("/id/{id}")
    public ResponseEntity<PersonsIdModel> postPersonById(@PathVariable("id") Long id, @RequestBody PersonsIdModel personsIdModel) {
        personsIdService.addNewPersonsId(personsIdModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personsIdModel);
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<PersonsIdModel>> getAllPersonsBySurname(@PathVariable("surname") String surname) {
        return ResponseEntity.ok(personsIdService.findPersonsBySurname(surname));
    }


//    @GetMapping("/persons")
//    public ResponseEntity<ClientsResponse> getAllWithPage(@RequestParam(required = false) Integer page,
//                                                          @RequestParam(required = false) Integer size,
//                                                          @RequestParam(required = false) String sort,
//                                                          @RequestParam(required = false) String column) {
//
//        Sort.Direction direction = Sort.Direction.fromString(sort);
//        if (page == null) {
//            page = 0;
//        }
//        if (size == null) {
//            size = 5;
//        }
//        Integer pageNumber = page >= 0 ? page : 0;
//        Integer sizeNumber = size > 0 ? size : 5;
//        int allClients = personsIdService.getAllPersonsIdList().size();
//        ClientsResponse clientsResponse = new ClientsResponse(personsIdService.findAllAndPage(pageNumber, sizeNumber, direction, column), allClients);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(clientsResponse);
//    }

    @GetMapping("/persons")
    public ResponseEntity<ClientsResponse> getAllWithFilterPageAndSort(@RequestParam(required = false) Integer page,
                                                                       @RequestParam(required = false) Integer size,
                                                                       @RequestParam(required = false) String sort,
                                                                       @RequestParam(required = false) String column,
                                                                       @RequestParam(required = false) String surname){

        Sort.Direction direction = Sort.Direction.fromString(sort);
        if (page == null){
            page = 0;
        }
        if (size == null){
            size = 5;
        }
        Integer pageNumber = page >= 0 ? page : 0;
        Integer sizeNumber = size > 0 ? size : 5;
        int allClients = personsIdService.getAllPersonsIdList().size();
        ClientsResponse clientsResponse;

        if( surname==null || surname.equals("")){
            clientsResponse = new ClientsResponse(personsIdService.findAllAndPage(pageNumber,sizeNumber,direction,column),allClients);
        } else{
            clientsResponse = new ClientsResponse(personsIdService.findAllBySurnameWithSort(surname,pageNumber,sizeNumber,direction,column),allClients);
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientsResponse);

    }

    @PutMapping("/id/{id}")
    public ResponseEntity<PersonsIdModel> putPersonById(@PathVariable("id") Long id, @RequestBody PersonsIdModel personsIdModel) {
        if(!personsIdService.isExist(id)){
            return (ResponseEntity<PersonsIdModel>) ResponseEntity.notFound();
        }
        personsIdModel.setId(id);
        personsIdService.addNewPersonsId(personsIdModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personsIdModel);
    }
}
