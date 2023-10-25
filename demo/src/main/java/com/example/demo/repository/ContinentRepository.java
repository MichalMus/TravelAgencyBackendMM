package com.example.demo.repository;

import com.example.demo.model.ContinentModel;
import com.example.demo.model.PersonsIdModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContinentRepository extends JpaRepository<ContinentModel, Long> {

    @Query("SELECT q from ContinentModel q  WHERE q.continentName LIKE :name%")
    Optional<ContinentModel> findContinentByContinentName(@Param("name")String name);

//    Optional<ContinentModel> findContinentByContinentName(String name);
}
