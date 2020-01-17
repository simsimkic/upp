package com.upp.nc.nc.services;

import com.upp.nc.nc.dtos.FormSubmissionDto;
import com.upp.nc.nc.entities.Magazine;
import com.upp.nc.nc.repositories.MagazineRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewMagazineService implements JavaDelegate {

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) {
        List<FormSubmissionDto> newMagazine = (List<FormSubmissionDto>)execution.getVariable("new_magazine");
        Magazine magazine = new Magazine();
        for (FormSubmissionDto formField : newMagazine) {
            if (formField.getFieldId().equals("issn")) {
                magazine.setIssn(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("title")) {
                magazine.setTitle(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("payment_method")) {
                magazine.setPaymentType(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("scientificFields")) {
                magazine.setScientificFields(formField.getFieldValue());
            }
        }
        magazineRepository.save(magazine);
    }
}
