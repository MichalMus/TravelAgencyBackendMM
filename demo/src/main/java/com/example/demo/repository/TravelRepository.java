package com.example.demo.repository;

import com.example.demo.model.TravelModel;
import com.example.demo.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface TravelRepository extends JpaRepository<TravelModel, Long> {

    List<TravelModel> findAllByPromotionIsTrue();

    List<TravelModel> findAllByStartDateIsAfterOrderByStartDate(Date date);

    List<TravelModel> findAllByStartDateIsAfterOrderByContinentModelId(Date date);

    List<TravelModel> findAllByStartDateIsAfterOrderByCountryModelId(Date date);

    List<TravelModel> findAllByCountryModel_CountryName(String name);

    @Modifying
    @Transactional
    @Query(value = "SELECT t FROM TravelModel t WHERE t.start = :airportStart")
    List<TravelModel> findByAirport(@Param("airportStart") Long id);

    List<TravelModel> findAllByStart_AirPortName(String nameAirport);

    List<TravelModel> findAllByHotelModel_HotelName(String nameHotel);

    List<TravelModel> findAllByStartDateIs(Date startDate); //sprawdzić czy to działa

    List<TravelModel> findAllByEndDateIs(Date endDate); //sprawdzić czy to działa

    List<TravelModel> findAllByHotelTypeIs(Type type); //jaki argument ma być przekazany do metody skoro to ENUM

    List<TravelModel> findAllByHotelModel_StarsNumber(Byte starsNumber);

    List<TravelModel> findAllByNumberOfDaysIs(Byte days);

    @Modifying
    @Transactional
    @Query(value = "SELECT t FROM TravelModel t WHERE t.startDate > :startDate1 AND t.startDate < :startDate2", nativeQuery = false)
    List<TravelModel> findAllByStartDateIsBetw(@Param("startDate1") Date startDate1, @Param("startDate2") Date startDate2);


    @Modifying
    @Transactional
    @Query(value = "SELECT t FROM TravelModel t WHERE t.endDate > :endDate1 AND t.endDate < :endDate2", nativeQuery = false)
    List<TravelModel> findAllByEndDateIsBetw(@Param("endDate1") Date endDate1, @Param("endDate2") Date endDate2);

    Optional<TravelModel> findById(Long id);

    void deleteTravelModelById(Long id);

    List<TravelModel> findAllByHotelModel_CityModel_CountryModel_CountryName(String name);

    List<TravelModel> findAllByHotelModel_CityModel_CityName(String name);

    List<TravelModel> findAllByHotelModel_CityModel_CountryModel_ContinentModel_ContinentName(String name);
}

