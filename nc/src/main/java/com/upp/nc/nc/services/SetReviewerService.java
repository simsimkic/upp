package com.upp.nc.nc.services;

import com.upp.nc.nc.dataServices.UserService;
import com.upp.nc.nc.entities.User;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SetReviewerService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution execution) {
        User user = userService.findByUsername((String)execution.getVariable("username"));
        user.setReviewer(true);
        user.setRole(2);
        userService.saveUser(user);
        identityService.createMembership(user.getUsername(), identityService.createGroupQuery().groupId("reviewers").singleResult().getId());
    }
}
