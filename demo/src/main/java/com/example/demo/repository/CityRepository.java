package com.example.demo.repository;

import com.example.demo.model.CityModel;
import com.example.demo.model.PersonsIdModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long> {
    @Query("SELECT c from CityModel c WHERE c.cityName LIKE :names%")
    Optional<CityModel>findCityByCityName (@Param("names") String names);

}
