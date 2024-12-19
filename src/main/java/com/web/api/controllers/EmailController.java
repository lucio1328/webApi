package com.web.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.api.service.EmailSender;

@RestController
public class EmailController {

    @Autowired
    private EmailSender emailSender;

    @Operation(summary = "Confirmation authentification via PIN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email envoyé avec succès",
                     content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Paramètre email manquant ou invalide",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = String.class)))
    })
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

