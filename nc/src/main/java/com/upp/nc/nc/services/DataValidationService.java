package com.upp.nc.nc.services;

import com.upp.nc.nc.dtos.FormSubmissionDto;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataValidationService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
        String password = "";
        String repeatedPassword = "";
        for (FormSubmissionDto formField : registration) {
            if(formField.getFieldId().equals("password")) {
                password = formField.getFieldValue();
            }
            if(formField.getFieldId().equals("password_repeated")) {
                repeatedPassword = formField.getFieldValue();
            }
        }

        Task task = taskService.createTaskQuery().taskId((String)execution.getVariable("taskId")).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        if (password.equals(repeatedPassword)) {
            runtimeService.setVariable(processInstanceId, "registrationFormDataValid", "true");
        } else {
            runtimeService.setVariable(processInstanceId, "registrationFormDataValid", "false");
            throw new Exception();
        }
    }

}
