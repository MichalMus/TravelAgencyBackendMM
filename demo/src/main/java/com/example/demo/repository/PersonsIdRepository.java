package com.example.demo.repository;

import com.example.demo.model.PersonsIdModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonsIdRepository extends JpaRepository<PersonsIdModel,Long> {
    List<PersonsIdModel> findAllByPersonSurnameEqualsIgnoreCase(String surname);
    @Query("Select p from PersonsIdModel p")
    List<PersonsIdModel> findAllPersonsIdModel(Pageable pageable);

    @Query("SELECT p from PersonsIdModel p WHERE p.personSurname LIKE :lastName%")
    List<PersonsIdModel>findAllPersonsBySurnameWithSortAndPaginate(@Param("lastName")String lastName, Pageable pageable);

}
