package com.covid.vaccine.Controller;


import com.covid.vaccine.model.Mail;
import com.covid.vaccine.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class TestEmail {

    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping("/test")
    public void sendEmail(){
        Mail newMail = new Mail();
        newMail.setFrom("youremail@email.com");
        newMail.setMailTo("targetemail@gmail.com");
        newMail.setSubject("Appointment Due for Vaccination");
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("name", "Test!");
        model.put("location", "Test");

        newMail.setProps(model);


        try {
            emailSenderService.sendEmail(newMail);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
