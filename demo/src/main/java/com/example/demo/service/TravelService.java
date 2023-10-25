package com.example.demo.service;

import com.example.demo.model.TravelModel;
import com.example.demo.model.Type;
import com.example.demo.repository.TravelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class TravelService {
    private final TravelRepository travelRepository;

    public TravelService(final TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public List<TravelModel> findByPromotion() {
        List<TravelModel> allByPromotionIsTrue = travelRepository.findAllByPromotionIsTrue();
        return allByPromotionIsTrue;
    }

    public List<TravelModel> findByStartDateAfterNow() {
        Date aktualDate = new Date();
        return travelRepository.findAllByStartDateIsAfterOrderByStartDate(aktualDate);
    }

    public List<TravelModel> findByStartDateAfterNowOrderByContinent() {
        Date aktualDate = new Date();
        return travelRepository.findAllByStartDateIsAfterOrderByContinentModelId(aktualDate);
    }

    public List<TravelModel> findByStartDateAfterNowOrderByCountry() {
        Date aktualDate = new Date();
        return travelRepository.findAllByStartDateIsAfterOrderByCountryModelId(aktualDate);
    }

    public List<TravelModel> findTravelsByAirportName(String airportName) {
        return travelRepository.findAllByStart_AirPortName(airportName);
    }

    public List<TravelModel> findTravelsByHotelName(String hotelName) {
        return travelRepository.findAllByHotelModel_HotelName(hotelName);
    }

    public List<TravelModel> findTravelsByStartDate(Date date) {
        return travelRepository.findAllByStartDateIs(date);
    }

    public List<TravelModel> findTravelsByEndDate(Date date) {
        return travelRepository.findAllByEndDateIs(date);
    }

    public List<TravelModel> findTravelsByHotelType(Type type) {  //czy dobry typ przekazany do metody
        return travelRepository.findAllByHotelTypeIs(type);
    }

    public List<TravelModel> findTravelsByHotelStars(Byte hotelStars) {
        return travelRepository.findAllByHotelModel_StarsNumber(hotelStars);
    }

    public List<TravelModel> findTravelsByCountry(String name) {
        return travelRepository.findAllByCountryModel_CountryName(name);
    }


    public List<TravelModel> findTravelsByStartDateIsNear(Date date1, Date date2) {
        return travelRepository.findAllByStartDateIsBetw(date1, date2);
    }

    public List<TravelModel> findTravelsByEndDateIsNear(Date date1, Date date2) {
        return travelRepository.findAllByEndDateIsBetw(date1, date2);
    }

    public List<TravelModel> findTravelsByNumberOfDays(Byte days) {
        return travelRepository.findAllByNumberOfDaysIs(days);
    }

    public Optional<TravelModel> findTravelById(Long id) {
        return travelRepository.findById(id);
    }

    public void addTravel(TravelModel travelModel){
        travelRepository.save(travelModel);
    }

    public void deleteTravel(Long id){
        travelRepository.deleteTravelModelById(id);
    }

    public void changeTravelModel(Long id, TravelModel travelModel){
        Optional<TravelModel> travelModel1 = travelRepository.findById(id);
        travelModel1.get().setStartDate(travelModel.getStartDate());
        travelModel1.get().setEndDate(travelModel.getEndDate());
        travelModel1.get().setNumberOfDays(travelModel.getNumberOfDays());
        travelModel1.get().setHotelType(travelModel.getHotelType());
        travelModel1.get().setAdultPrice(travelModel.getAdultPrice());
        travelModel1.get().setChildPrice(travelModel.getChildPrice());
        travelModel1.get().setPromotion(travelModel.getPromotion());
        travelModel1.get().setAdultsNumber(travelModel.getAdultsNumber());
        travelModel1.get().setChildrenNumber(travelModel.getChildrenNumber());

        travelModel1.get().setStart(travelModel.getStart());
        travelModel1.get().setDestination(travelModel.getDestination());
        travelModel1.get().setHotelModel(travelModel.getHotelModel());
        travelModel1.get().setCountryModel(travelModel.getCountryModel());
        travelModel1.get().setContinentModel(travelModel.getContinentModel());
        travelRepository.save(travelModel1.get());
    }

    public List<TravelModel> findAllTravel(){
        return travelRepository.findAll();
    }

    public List<TravelModel> findTravelsInContinentFromHotelModel(String name) {
        return travelRepository.findAllByHotelModel_CityModel_CountryModel_ContinentModel_ContinentName(name);
    }
    public List<TravelModel> findTravelsInCountryFromHotelModel(String name) {
        return travelRepository.findAllByHotelModel_CityModel_CountryModel_CountryName(name);
    }
    public List<TravelModel> findTravelsInCityFromHotelModel(String name) {
        return travelRepository.findAllByHotelModel_CityModel_CityName(name);
    }







}

