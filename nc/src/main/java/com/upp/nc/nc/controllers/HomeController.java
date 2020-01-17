package com.upp.nc.nc.controllers;

import java.util.ArrayList;
import java.util.List;

import com.upp.nc.nc.dtos.ReviewerApprovalDto;
import com.upp.nc.nc.entities.Magazine;
import com.upp.nc.nc.repositories.MagazineRepository;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upp.nc.nc.dtos.FormFieldsDto;
import com.upp.nc.nc.dtos.TaskDto;

@Controller
@RequestMapping("/welcome")
public class HomeController {
    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    MagazineRepository magazineRepository;

    @GetMapping(path="registration", produces = "application/json")
    public @ResponseBody FormFieldsDto startRegistrationProcess() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("registration_process");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        //save process id
        runtimeService.setVariable(processInstance.getId(), "processId", processInstance.getId());

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());

        List<FormField> allFormFields = taskFormData.getFormFields();

        return new FormFieldsDto(task.getId(), processInstance.getId(), allFormFields);
    }

    @GetMapping(path="newMagazine", produces = "application/json")
    public @ResponseBody FormFieldsDto createNewMagazine() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("create_magazine_process");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());

        List<FormField> allFormFields = taskFormData.getFormFields();

        return new FormFieldsDto(task.getId(), processInstance.getId(), allFormFields);
    }

    @GetMapping(path = "/get/tasks/{processDefinitionKey}", produces = "application/json")
    public @ResponseBody ResponseEntity<TaskDto> get(@PathVariable String processDefinitionKey) {
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).list();
        Task task = tasks.get(0);
        TaskDto taskDto = new TaskDto(task.getId(), task.getName(), task.getAssignee());
        return new ResponseEntity(taskDto, HttpStatus.OK);
    }

    @GetMapping(path = "/get/userTasks/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity getUserTasks(@PathVariable String username) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
        List<TaskDto> dtos = new ArrayList<>();
        for (Task t : tasks) {
            dtos.add(new TaskDto(t.getId(), t.getName(), t.getAssignee()));
        }
        return new ResponseEntity(dtos, HttpStatus.OK);
    }

    // this is for reviewer approval
    @GetMapping(path = "/data/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity getDataFromTaskId(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ReviewerApprovalDto dto = new ReviewerApprovalDto(
                (String) runtimeService.getVariable(processInstanceId, "name"),
                (String) runtimeService.getVariable(processInstanceId, "surname"),
                (String) runtimeService.getVariable(processInstanceId, "email"),
                (String) runtimeService.getVariable(processInstanceId, "city"),
                (String) runtimeService.getVariable(processInstanceId, "country")
        );
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping(path = "/data/magazineName/{taskId}", produces="application/json")
    public @ResponseBody ResponseEntity getMagazineForStaffSetup(@PathVariable String taskId) {
        String processId = getProcessId(taskId);
        String issn = (String) runtimeService.getVariable(processId, "issn");
        Magazine magazine = magazineRepository.findByIssn(issn);
        return new ResponseEntity(magazine, HttpStatus.OK);
    }

    private String getProcessId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task.getProcessInstanceId();
    }

    @PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String user = (String) runtimeService.getVariable(processInstanceId, "username");
        taskService.claim(taskId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/tasks/complete/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> complete(@PathVariable String taskId) {
        Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.complete(taskId);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
        List<TaskDto> dtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
            dtos.add(t);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
