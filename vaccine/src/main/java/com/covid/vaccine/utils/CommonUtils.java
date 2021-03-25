package com.covid.vaccine.utils;


import com.covid.vaccine.model.Mail;
import com.covid.vaccine.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommonUtils {

    @Autowired
    EmailSenderService emailSenderService;

    public void sendEmail(Map<String,String> emailProps){
        Mail newMail = new Mail();
        newMail.setFrom(emailProps.get("from"));
        newMail.setMailTo(emailProps.get("to"));
        newMail.setSubject(emailProps.get("subject"));
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("name", emailProps.get("name"));
        model.put("location", emailProps.get("location"));

        newMail.setProps(model);


        try {
            emailSenderService.sendEmail(newMail);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
