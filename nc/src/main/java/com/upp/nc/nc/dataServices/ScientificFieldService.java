package com.upp.nc.nc.dataServices;

import com.upp.nc.nc.entities.ScientificField;
import com.upp.nc.nc.repositories.ScientificFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientificFieldService {

    @Autowired
    ScientificFieldRepository scientificFieldRepository;

    public List<ScientificField> findAll() {
        return scientificFieldRepository.findAll();
    }
}
