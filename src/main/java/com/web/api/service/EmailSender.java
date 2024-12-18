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

    public void sendEmailWithTemplate(String recipientEmail, String name) {
        String subject = "Exemple d'email avec Spring Boot";
        String htmlContent = emailTemplateService.generateEmailContent(name);
        System.out.println(htmlContent);

        try {
            emailService.sendHtmlEmail(recipientEmail, subject, htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Gère les erreurs ici
        }
    }
}

