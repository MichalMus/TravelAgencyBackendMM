package com.example.demo.repository;

import com.example.demo.model.HotelModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelModel, Long> {

    List<HotelModel> findAllByCityModel_CityName(String cityName);

    @Query("SELECT h from HotelModel h")
    List<HotelModel> findAllHotels(Pageable pageable);

    @Query("SELECT h FROM HotelModel h WHERE h.cityModel.cityName LIKE :name%")
    List<HotelModel> findHotelsByCity(@Param("name") String name, Pageable pageable);
}
