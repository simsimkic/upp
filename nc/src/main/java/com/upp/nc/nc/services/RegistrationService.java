package com.upp.nc.nc.services;

import com.upp.nc.nc.controllers.UserController;
import com.upp.nc.nc.dataServices.RoleService;
import com.upp.nc.nc.dtos.FormSubmissionDto;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserController userController;

    @Autowired
    RoleService roleService;

    @Override
    public void execute(DelegateExecution execution) {
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
        User user = identityService.newUser("");
        String reviewer = "";
        for (FormSubmissionDto formField : registration) {
            if (formField.getFieldId().equals("username")) {
                user.setId(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("password")) {
                user.setPassword(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("name")) {
                user.setFirstName(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("surname")) {
                user.setLastName(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("email")) {
                user.setEmail(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("reviewer")) {
                reviewer = formField.getFieldValue();
            }
        }
        identityService.saveUser(user);
        if (reviewer.equals("")) {
            identityService.createMembership(user.getId(), identityService.createGroupQuery().groupId("users").singleResult().getId());
        }
    }



}
