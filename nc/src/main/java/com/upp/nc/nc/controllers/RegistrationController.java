package com.upp.nc.nc.controllers;

import com.upp.nc.nc.dataServices.UserService;
import com.upp.nc.nc.dtos.FormSubmissionDto;
import com.upp.nc.nc.entities.User;
import com.upp.nc.nc.entities.VerificationToken;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    UserService userService;

    @Autowired
    HomeController homeController;

    @PostMapping(path = "/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity registerUser(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);
        runtimeService.setVariable(processInstanceId, "taskId", taskId);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/verification/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity verifyAccount(@RequestBody String token, @PathVariable String taskId) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken != null) {
            User user = verificationToken.getUser();
            Calendar cal = Calendar.getInstance();
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) > 0) {
                user.setActive(true);
                userService.saveUser(user);
                runtimeService.setVariable(processInstanceId, "verified", "true");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                userService.delete(user.getUsername());
                runtimeService.setVariable(processInstanceId, "verified", "false");
                homeController.complete(taskId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }
        return map;
    }
}
