package com.upp.nc.nc.controllers;

import com.upp.nc.nc.dtos.LoginDto;
import com.upp.nc.nc.entities.User;
import com.upp.nc.nc.dataServices.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HomeController homeController;

    public User saveUser(User user) {
        return userService.saveUser(user);
    }

    @GetMapping(path = "users", produces = "application/json")
    public @ResponseBody ResponseEntity getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @PostMapping(path = "login", produces = "application/json")
    public ResponseEntity login(@RequestBody LoginDto dto) {
        User user = userService.login(dto);
        if (user != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "reviewer/approve/{taskId}")
    public ResponseEntity approveReviewer(@PathVariable String taskId) {
        runtimeService.setVariable(getProcessId(taskId), "reviewer_approved", "true");
        homeController.complete(taskId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "reviewer/deny/{taskId}")
    public ResponseEntity denyReviewer(@PathVariable String taskId) {
        runtimeService.setVariable(getProcessId(taskId), "reviewer_approved", "false");
        homeController.complete(taskId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private String getProcessId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task.getProcessInstanceId();
    }

    @GetMapping(path = "reviewers/{taskId}")
    public ResponseEntity getReveiwers(@PathVariable String taskId) {
        String fields = (String)runtimeService.getVariable(getProcessId(taskId), "scientificFields");
        return new ResponseEntity(userService.getReviewers(fields), HttpStatus.OK);
    }

    @GetMapping(path = "editors/{taskId}")
    public ResponseEntity getEditors(@PathVariable String taskId) {
        String fields = (String)runtimeService.getVariable(getProcessId(taskId), "scientificFields");
        return new ResponseEntity(userService.getEditors(fields), HttpStatus.OK);
    }
}
