package com.upp.nc.nc.services;

import com.upp.nc.nc.entities.Magazine;
import com.upp.nc.nc.repositories.MagazineRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SetChiefEditor implements JavaDelegate {

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) {
        String issn = (String)execution.getVariable("issn");
        Magazine magazine = magazineRepository.findByIssn(issn);
        magazine.setChiefEditor((String)execution.getVariable("initiator"));
        Task task = taskService.createTaskQuery().taskId((String)execution.getVariable("taskId")).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "chief_editor", magazine.getChiefEditor());
    }
}
