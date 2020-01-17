package com.upp.nc.nc.handlers;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessHandler implements ExecutionListener {

    @Autowired
    IdentityService identityService;

    @Override
    public void notify(DelegateExecution execution) {
        List<User> users = identityService.createUserQuery().userIdIn("pera", "mika", "zika").list();
        if(users.isEmpty() ) {
            createAndSaveUser("Pera");
            createAndSaveUser("Mika");
            createAndSaveUser("Zika");
        }
    }

    private void createAndSaveUser(String name) {
        User user = identityService.newUser(name.toLowerCase());
        user.setEmail(name.toLowerCase()+"@mail.com");
        user.setFirstName(name);
        user.setLastName(name.substring(0, name.length()-1)+"ic");
        user.setPassword("pass");
        identityService.saveUser(user);
    }
}
