package com.web.api.service;

import java.sql.Timestamp;

import org.apache.el.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.models.ValidationConnectionEntity;
import com.web.api.repository.UtilisateurRepository;
import com.web.api.repository.ValidationConnectionRepository;

import jakarta.mail.MessagingException;

@Service                                                                                                
public class EmailSender {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ValidationConnectionRepository validationConnectionRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;


    public void envoyer_email_confirmation(String recepteur, String pin, int idUtilisateur) {
        String subject = "Confirmation connexion";
        // String htmlContent = emailTemplateService.generer_email_connexion("http://localhost:8081/api/confirmerConnexion/" + idUtilisateur + "/" + pin);
        String htmlContent = emailTemplateService.generer_email_connexion(pin);
        ValidationConnectionEntity validationEntity = new ValidationConnectionEntity();
        validationEntity.setPin(pin);
        validationEntity.setDaty(new Timestamp(System.currentTimeMillis()));
        validationEntity.setIdStatut(1);
        validationEntity.setIdUtilisateur(idUtilisateur);

        
        try {
            emailService.envoyer_html_via_email(recepteur, subject, htmlContent);
            
            this.validationConnectionRepository.save(validationEntity);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void envoyer_email_validation_inscription(String recepteur, String lien) {
        String subject = "Confirmation inscription";
        String htmlContent = emailTemplateService.generer_email_confirmation_inscription(lien);

        try {
            emailService.envoyer_html_via_email(recepteur, subject, htmlContent);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            // Gère les erreurs ici
        }
    }

    public void envoyer_email_reinitialiser_tentative(String recepteur, String lien) {
        String subject = "Reinitialiser tentative";
        String htmlContent = emailTemplateService.reinitialiser_tentative(lien);

        try {
            emailService.envoyer_html_via_email(recepteur, subject, htmlContent);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

