package com.example.demo.repository;

import com.example.demo.model.AirportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<AirportModel, Long> {

    List<AirportModel> findAllByCityModel_CityNameEqualsIgnoreCase(String name);

}
