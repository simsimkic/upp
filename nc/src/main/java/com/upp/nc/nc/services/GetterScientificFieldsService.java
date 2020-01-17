package com.upp.nc.nc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upp.nc.nc.dataServices.ScientificFieldService;
import com.upp.nc.nc.entities.ScientificField;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetterScientificFieldsService implements JavaDelegate {

    @Autowired
    ScientificFieldService scientificFieldService;

    @Override
    public void execute(DelegateExecution execution) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<ScientificField> scientificFields = scientificFieldService.findAll();
        Map<String, String> fields = new HashMap<>();
        for (ScientificField field : scientificFields) {
            fields.put(field.getCode(), field.getName());
        }
        execution.setVariable("AVAILABLE_SCIENTIFIC_FIELDS",  mapper.writeValueAsString(fields));
    }
}
