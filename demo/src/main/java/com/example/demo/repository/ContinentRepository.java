package com.example.demo.repository;

import com.example.demo.model.ContinentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepository extends JpaRepository<ContinentModel, Long> {
}
