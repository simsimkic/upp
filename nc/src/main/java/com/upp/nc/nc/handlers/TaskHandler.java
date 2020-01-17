package com.upp.nc.nc.handlers;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHandler implements TaskListener {

    @Autowired
    IdentityService identityService;

    public void notify(DelegateTask delegateTask) {
        List<User> users = identityService.createUserQuery().userIdIn("pera", "mika", "zika").list();

        DelegateExecution execution = delegateTask.getExecution();
        execution.setVariable("users", users);
    }
}
