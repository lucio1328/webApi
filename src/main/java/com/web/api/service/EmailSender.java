package com.web.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service                                                                                                
public class EmailSender {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    public void envoyer_email_confirmation(String recepteur, String pin) {
        String subject = "Confirmation inscription";
        String htmlContent = emailTemplateService.generer_email_connexion(pin);

        try {
            emailService.envoyer_html_via_email(recepteur, subject, htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Gère les erreurs ici
        }
    }
}

