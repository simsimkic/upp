package com.upp.nc.nc.controllers;

import com.upp.nc.nc.dataServices.UserService;
import com.upp.nc.nc.dtos.*;
import com.upp.nc.nc.entities.Magazine;
import com.upp.nc.nc.entities.ScientificField;
import com.upp.nc.nc.entities.User;
import com.upp.nc.nc.repositories.MagazineRepository;
import com.upp.nc.nc.repositories.ScientificFieldRepository;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

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

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    ScientificFieldRepository scientificFieldRepository;

    @GetMapping(produces = "application/json")
    public @ResponseBody ResponseEntity getAllMagazines() {
        List<Magazine> magazines = magazineRepository.findAllByActive(true);
        return new ResponseEntity(magazines, HttpStatus.OK);
    }

    @PostMapping(path = "/{taskId}/{username}", produces = "application/json")
    public @ResponseBody
    ResponseEntity createMagazine(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId,
                                  @PathVariable String username) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "new_magazine", dto);
        runtimeService.setVariable(processInstanceId, "taskId", taskId);
        runtimeService.setVariable(processInstanceId, "initiator", username);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{issn}", produces = "application/json")
    public @ResponseBody
    ResponseEntity updateMagazine(@RequestBody UpdateMagazineDto dto, @PathVariable String issn) {
        Magazine magazine = magazineRepository.findByIssn(issn);
        magazine.setTitle(dto.getTitle());
        magazine.setPaymentType(dto.getPayment_method());
        magazineRepository.save(magazine);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/editorsAndReviewers/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity addEditorsAndReviewers(@RequestBody EditorsAndReviewersDto dto, @PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        String issn = (String)runtimeService.getVariable(processInstanceId, "issn");
        Magazine magazine = magazineRepository.findByIssn(issn);
        magazine.setEditors(dto.getEditors());
        magazine.setReviewers(dto.getReviewers());
        magazineRepository.save(magazine);

        setMagazine(dto.getEditors(), magazine.getIssn());
        setMagazine(dto.getReviewers(), magazine.getIssn());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/data/{taskId}")
    public @ResponseBody ResponseEntity getDataForMagazineApproval(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        Magazine magazine = magazineRepository.findByIssn((String) runtimeService.getVariable(processInstanceId, "issn"));
        User chiefEditor = userService.findByUsername(magazine.getChiefEditor());

        String editors = getStaff(magazine.getEditors());
        String reviewers = getStaff(magazine.getReviewers());
        String fields = getScientificFields(magazine.getScientificFields());

        MagazineApprovalDto dto = new MagazineApprovalDto(magazine.getTitle(), magazine.getIssn(), fields,
                magazine.getPaymentType(), chiefEditor.getName() + " " + chiefEditor.getSurname(),
                editors, reviewers);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping(path="/updateForm/{taskId}")
    public @ResponseBody FormFieldsDto getUpdateForm(@PathVariable String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);

        List<FormField> allFormFields = taskFormData.getFormFields();

        return new FormFieldsDto(taskId, getProcessId(taskId), allFormFields);
    }

    @GetMapping(path = "/approve/{taskId}")
    public ResponseEntity approveMagazine(@PathVariable String taskId) {
        runtimeService.setVariable(getProcessId(taskId), "magazine_approved", "true");
        homeController.complete(taskId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/deny/{taskId}")
    public ResponseEntity denyMagazine(@PathVariable String taskId) {
        runtimeService.setVariable(getProcessId(taskId), "magazine_approved", "false");
        homeController.complete(taskId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private String getProcessId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task.getProcessInstanceId();
    }

    private String getScientificFields(String codes) {
        if (!codes.contains(",")) {
            return codes;
        }
        String result = "";
        for (String c : codes.split(",")) {
            ScientificField field = scientificFieldRepository.findByCode(c);
            result += field.getName() + ", ";
        }
        result = result.substring(0, result.length()-2);
        return result;
    }

    private String getStaff(String staff) {
        if (!staff.contains(",")) {
            return staff;
        }
        String result = "";
        for (String s : staff.split(",")) {
            User user = userService.findByUsername(s);
            result += user.getName() + " " + user.getSurname() + ", ";
        }
        result = result.substring(0, result.length()-2);
        return result;
    }

    private void setMagazine(String users, String issn) {
        for (String u : users.split(",")) {
            User user = userService.findByUsername(u);
            if (user.getMagazines() == null) {
                user.setMagazines(issn);
            } else {
                user.setMagazines(user.getMagazines() + "," + issn);
            }
            userService.saveUser(user);
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
