package com.upp.nc.nc.services;

import com.upp.nc.nc.entities.Magazine;
import com.upp.nc.nc.repositories.MagazineRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivateMagazineService implements JavaDelegate {

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void execute(DelegateExecution execution) {
        String issn = (String)execution.getVariable("issn");
        Magazine magazine = magazineRepository.findByIssn(issn);
        magazine.setActive(true);
        magazineRepository.save(magazine);
    }
}
