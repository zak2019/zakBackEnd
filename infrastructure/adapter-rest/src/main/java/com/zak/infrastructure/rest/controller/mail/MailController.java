package com.zak.infrastructure.rest.controller.mail;

import com.zak.infrastructure.provider.EmailData;
import com.zak.infrastructure.provider.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class MailController {

    private final EmailService emailService;

    @Autowired
    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public Boolean sendMail(@RequestBody EmailData emailData) {
       return emailService.sendEmail(
               emailData.getTo(),
               "test subject",
               "<h3>mail title</h1><p>mail content</p>");
    }
}
