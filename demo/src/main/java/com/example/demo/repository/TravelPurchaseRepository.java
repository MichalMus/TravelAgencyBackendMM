package com.example.demo.repository;

import com.example.demo.model.TravelPurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelPurchaseRepository extends JpaRepository<TravelPurchaseModel,Long> {
    List<TravelPurchaseModel> findAllByTravelModel_Id(Long id);
}
