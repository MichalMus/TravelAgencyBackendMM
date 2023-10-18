package com.example.demo.service;

import com.example.demo.model.TravelModel;
import com.example.demo.model.TravelPurchaseModel;
import com.example.demo.repository.TravelPurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class TravelPurchaseService {

    private final TravelPurchaseRepository travelPurchaseRepository;

    public TravelPurchaseService(final TravelPurchaseRepository travelPurchaseRepository){
        this.travelPurchaseRepository=travelPurchaseRepository;
    }

    public List<TravelPurchaseModel> getAllTravelPurchaseModels(){
        return travelPurchaseRepository.findAll();
    }

    public void addNewTravelPurchaseModel(TravelPurchaseModel travelPurchaseModel){
        travelPurchaseRepository.save(travelPurchaseModel);
    }

    public void deleteTravelPurchaseModelById(Long id){
        travelPurchaseRepository.deleteById(id);
    }

    public List<TravelPurchaseModel> getAllTravelPurchaseModelsByTravelModel(Long id) {
        return travelPurchaseRepository.findAllByTravelModel_Id(id);
    }
    public Optional<TravelPurchaseModel> findTravelPurchaseById(Long id) {
        return travelPurchaseRepository.findById(id);
    }

    public double getTravelPrice(Long id) {
        Optional<TravelPurchaseModel> travelPurchaseModel = findTravelPurchaseById(id);
        TravelModel travelModel = travelPurchaseModel.get().getTravelModel();
        Byte AdultsNumber = travelPurchaseModel.get().getAdultsNumber();
        Byte ChildrenNumber = travelPurchaseModel.get().getChildrenNumber();
        Integer AdultPrice = travelModel.getAdultPrice();
        Integer ChildrenPrice = travelModel.getChildPrice();
        return ((AdultsNumber * AdultPrice) + (ChildrenNumber * ChildrenPrice));

    }

    public void changeNumberOfPlacesInTravel(Long id) {
        Optional<TravelPurchaseModel> travelPurchaseModel = findTravelPurchaseById(id);
        TravelModel travelModel = travelPurchaseModel.get().getTravelModel();
        Byte newAdults = (byte) (travelModel.getAdultsNumber() - travelPurchaseModel.get().getAdultsNumber());
        Byte newChildren = (byte) (travelModel.getChildrenNumber() - travelPurchaseModel.get().getChildrenNumber());
        travelModel.setAdultsNumber(newAdults);
        travelModel.setChildrenNumber(newChildren);
        travelPurchaseModel.get().setTravelModel(travelModel);
        travelPurchaseRepository.save(travelPurchaseModel.orElseThrow());
    }

    public void changeTravelPurchaseModel(Long id, TravelPurchaseModel travelPurchaseModel) {
        Optional<TravelPurchaseModel> travelPurchaseModel1 = travelPurchaseRepository.findById(id);
        if (travelPurchaseModel1.isPresent()) {
            travelPurchaseModel1.get().setTravelModel(travelPurchaseModel.getTravelModel());
            travelPurchaseModel1.get().setAdultsNumber(travelPurchaseModel.getAdultsNumber());
            travelPurchaseModel1.get().setChildrenNumber(travelPurchaseModel.getChildrenNumber());
            travelPurchaseRepository.save(travelPurchaseModel1.get());
        } else {
            travelPurchaseRepository.save(travelPurchaseModel);
        }
    }
}
