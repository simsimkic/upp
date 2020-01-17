package com.upp.nc.nc.repositories;

import com.upp.nc.nc.entities.ScientificField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScientificFieldRepository extends JpaRepository<ScientificField, String> {

    ScientificField findByCode(String code);
}
