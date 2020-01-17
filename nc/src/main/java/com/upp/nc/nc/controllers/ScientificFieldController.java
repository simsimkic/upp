package com.upp.nc.nc.controllers;

import com.upp.nc.nc.dataServices.ScientificFieldService;
import com.upp.nc.nc.entities.ScientificField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/scientificFields")
public class ScientificFieldController {

    @Autowired
    ScientificFieldService scientificFieldService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ScientificField>> findAll() {
        List<ScientificField> allFields = scientificFieldService.findAll();
        return new ResponseEntity<>(allFields, HttpStatus.OK);
    }
}
