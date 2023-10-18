package com.example.demo.controller;

import com.example.demo.exception.TravelNotFoundException;
import com.example.demo.model.DateDTO;
import com.example.demo.model.TravelModel;
import com.example.demo.model.Type;
import com.example.demo.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelController {

    private final TravelService travelService;

    @GetMapping("/promotion")
    public ResponseEntity<List<TravelModel>> getAllTravels() {
        return ResponseEntity.ok(travelService.findByPromotion());
    }

    @GetMapping("/near")
    public ResponseEntity<List<TravelModel>> getAllTrabyDates() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travelService.findByStartDateAfterNow());
    }

    @GetMapping("/near/continents")
    public ResponseEntity<List<TravelModel>> getAllTravelsByDatesOrderByContinents() {
        return ResponseEntity.ok(travelService.findByStartDateAfterNowOrderByContinent());
    }

    @GetMapping("/near/countries")
    public ResponseEntity<List<TravelModel>> getAllTravelsByDatesOrderByCountries() {
        return ResponseEntity.ok(travelService.findByStartDateAfterNowOrderByCountry());
    }

    @GetMapping("/near/between")
    public ResponseEntity<List<TravelModel>> getAllTravelsNearThisDate(@RequestBody DateDTO dateDTO) {
        return ResponseEntity.ok(travelService.findTravelsByStartDateIsNear(dateDTO.getDate1(), dateDTO.getDate2()));
    }

    @GetMapping("/near/{date1}/{date2}")
    public ResponseEntity<List<TravelModel>> getAllTravelsNearThisDate(@PathVariable("date1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1, @PathVariable("date2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2) {
        return ResponseEntity.ok(travelService.findTravelsByStartDateIsNear(date1, date2));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<TravelModel>> getAllByCountry(@PathVariable("country") String countryName) {
        return ResponseEntity.ok(travelService.findTravelsByCountry(countryName));

    }

    @GetMapping("/airport/{airport}")
    public ResponseEntity<List<TravelModel>> getAllByAirport(@PathVariable("airport") String airport) {
        return ResponseEntity.ok(travelService.findTravelsByAirportName(airport));
    }

    @GetMapping("/hotel/{hotel}")
    public ResponseEntity<List<TravelModel>> getAllByHotel(@PathVariable("hotel") String hotel) {
        return ResponseEntity.ok(travelService.findTravelsByHotelName(hotel));
    }

    @GetMapping("/hoteltype/{type}")
    public ResponseEntity<List<TravelModel>> getAllByHotelType(@PathVariable("type") Type type) {
        return ResponseEntity.ok(travelService.findTravelsByHotelType(type));
    }


    @GetMapping("/endDate/between")
    public ResponseEntity<List<TravelModel>> getAllTravelsNearThisEndDate(@RequestBody DateDTO dateDTO) {
        return ResponseEntity.ok(travelService.findTravelsByEndDateIsNear(dateDTO.getDate1(), dateDTO.getDate2()));
    }

    @GetMapping("/endDate/{date1}/{date2}")
    public ResponseEntity<List<TravelModel>> getAllTravelsNearThisEndDate(@PathVariable("date1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1, @PathVariable("date2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2) {
        return ResponseEntity.ok(travelService.findTravelsByEndDateIsNear(date1, date2));
    }

    @GetMapping("/hotelStars/{stars}")
    public ResponseEntity<List<TravelModel>> getAllTravelsByHotelStars(@PathVariable("stars") Byte stars) {
        return ResponseEntity.ok(travelService.findTravelsByHotelStars(stars));
    }

    @GetMapping("/numberOfDays/{numbers}")
    public ResponseEntity<List<TravelModel>> getAllTravelsByNumbersOfDays(@PathVariable("numbers") Byte numbers) {
        return ResponseEntity.ok(travelService.findTravelsByNumberOfDays(numbers));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TravelModel> getTrById(@PathVariable("id") Long id) {
        final TravelModel travelModel = travelService.findTravelById(id)
                .orElseThrow(() -> new TravelNotFoundException(id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travelModel);
    }

    @GetMapping("/startDate/{startDate}")
    public ResponseEntity<List<TravelModel>> getAllTravelsInThisDate(@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return ResponseEntity.ok(travelService.findTravelsByStartDate(startDate));
    }

    @GetMapping("/endDate/{endDate}")
    public ResponseEntity<List<TravelModel>> getAllTravelsInThisEndDate(@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(travelService.findTravelsByEndDate(endDate));
    }

    @PostMapping("/addTravel")
    public ResponseEntity addNewTravel(@RequestBody TravelModel travelModel) {
        travelService.addTravel(travelModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travelModel);

    }

    @DeleteMapping("/deleteTravel/{id}")
    public ResponseEntity deleteTravelById(@PathVariable("id") Long id) {
        try {
            travelService.deleteTravel(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e){ //spradzić czy dobrze zapisane tre wyjątki i poprawić WE WSZYSTKICH CONTROLERACH
            e.printStackTrace();
//            throw new TravelNotFoundException(id);
        }

        return null;
    }

    @PostMapping("/id/{id}")
    public ResponseEntity<TravelModel> postTrById(@PathVariable("id") Long id, @RequestBody TravelModel travelModel) {
        travelService.changeTravelModel(id,travelModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travelModel);
    }

    @GetMapping("/allTravels")
    public ResponseEntity<List<TravelModel>> getAllTravel() {
        final List<TravelModel> travels = travelService.findAllTravel();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(travels);
    }

    @GetMapping("/country2/{country}")
    public ResponseEntity<List<TravelModel>> getAllByCountryFromHotel(@PathVariable("country") String countryName) {
        return ResponseEntity.ok(travelService.findTravelsInCountryFromHotelModel(countryName));

    }

    @GetMapping("/continent2/{continent}")
    public ResponseEntity<List<TravelModel>> getAllByContinentFromHotel(@PathVariable("continent") String continentName) {
        return ResponseEntity.ok(travelService.findTravelsInContinentFromHotelModel(continentName));
    }
    @GetMapping("/city2/{city}")
    public ResponseEntity<List<TravelModel>> getAllByCityFromHotel(@PathVariable("city") String cityName) {
        return ResponseEntity.ok(travelService.findTravelsInCityFromHotelModel(cityName));

    }

}
