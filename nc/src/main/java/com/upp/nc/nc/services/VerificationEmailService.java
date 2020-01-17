package com.upp.nc.nc.services;

import com.upp.nc.nc.dataServices.RoleService;
import com.upp.nc.nc.dataServices.UserService;
import com.upp.nc.nc.dtos.FormSubmissionDto;
import com.upp.nc.nc.entities.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;


@Service
public class VerificationEmailService implements JavaDelegate {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Override
    public void execute(DelegateExecution execution) throws MessagingException {
        // TODO : receiver
        String token = UUID.randomUUID().toString();
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
        User user = createUserFromFormData(registration);
        userService.saveUser(user);
        userService.createVerificationToken(user, token);

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        String address = "167simonap@gmail.com";
        helper.setTo(address);

        helper.setSubject("Verify your account");
        helper.setText("<a href=\"http://localhost:4201/verification/" + token + "\">Link</a>", true);

        javaMailSender.send(msg);
    }

    private com.upp.nc.nc.entities.User createUserFromFormData(List<FormSubmissionDto> registration) {
        com.upp.nc.nc.entities.User user = new com.upp.nc.nc.entities.User();
        for (FormSubmissionDto formField : registration) {
            if (formField.getFieldId().equals("username")) {
                user.setUsername(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("password")) {
                user.setPassword(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("name")) {
                user.setName(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("surname")) {
                user.setSurname(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("email")) {
                user.setEmail(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("city")) {
                user.setCity(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("country")) {
                user.setCountry(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("title") && !formField.getFieldValue().equals("")) {
                user.setTitle(formField.getFieldValue());
            }
            if (formField.getFieldId().equals("reviewer") && !formField.getFieldValue().equals("")) {
                user.setReviewer(Boolean.parseBoolean(formField.getFieldValue()));
            }
            if (formField.getFieldId().equals("scientificFields")) {
                user.setScientificFields(formField.getFieldValue());
            }
        }
        user.setRole(roleService.findByName("user").getId());
        return user;
    }
}

