package com.example.demo.repository;

import com.example.demo.model.ContinentModel;
import com.example.demo.model.CountryModel;
import com.example.demo.model.PersonsIdModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryModel, Long> {
    @Query("SELECT v from CountryModel v  WHERE v.countryName LIKE :name%")
    Optional<CountryModel> findCountryByCountryName(@Param("name")String name);

}
