package com.example.demo.service;

import com.example.demo.model.TravelModel;
import com.example.demo.model.TravelPurchaseModel;
import com.example.demo.repository.TravelPurchaseRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
@RunWith(MockitoJUnitRunner.class)
public class TravelPurchaseTest2 {
    @Mock
    TravelPurchaseRepository travelPurchaseRepository;
    @InjectMocks
    TravelPurchaseService travelPurchaseService;
    @Test
    public void cos(){
        //given
        TravelModel travelModel = new TravelModel();
        travelModel.setAdultPrice(100);
        travelModel.setChildPrice(75);

        TravelPurchaseModel travelPurchaseModel = new TravelPurchaseModel();
        travelPurchaseModel.setId(1L);
        travelPurchaseModel.setAdultsNumber((byte) 3);
        travelPurchaseModel.setChildrenNumber((byte) 3);
        travelPurchaseModel.setTravelModel(travelModel);

        //when
        Mockito.when(travelPurchaseRepository.findById(travelPurchaseModel.getId())).thenReturn(Optional.of(travelPurchaseModel));

        //then
       double expected = 525;
        double result = travelPurchaseService.getTravelPrice(travelPurchaseModel.getId());
        Assertions.assertThat(result).isEqualTo(expected);

    }
}
