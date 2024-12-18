package com.web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.api.service.EmailSender;

@RestController
public class EmailController {

    @Autowired
    private EmailSender emailSender;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String email) {
        String pin = "huhu";
        emailSender.envoyer_email_confirmation(email, pin);
        return "Email envoyé avec succès à " + email;
    }

    @GetMapping("/send-email-inscription")
    public String sendEmailInscription(@RequestParam String email) {
        String lien = "huhu";
        emailSender.envoyer_email_validation_inscription(email, lien);
        return "Email envoyé avec succès à " + email;
    }
}

