package com.upp.nc.nc.repositories;

import com.upp.nc.nc.entities.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {

    Magazine findByIssn(String issn);
    List<Magazine> findAllByActive(boolean active);
}
