package com.web.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailTemplateService {

    @Autowired
    private TemplateEngine template_engine;

    public String generer_email_connexion(String content) {
        Context context = new Context();
        context.setVariable("content", content);

        return template_engine.process("emailPinTemplate", context);
    }

    public String generer_email_confirmation_inscription(String content) {
        Context context = new Context();
        context.setVariable("content", content);

        return template_engine.process("emailConfirmationInscriptionTemplate", context);
    }

    public String reinitialiser_tentative(String content) {
        Context context = new Context();
        context.setVariable("content", content);

        return template_engine.process("emailReinitialiserTentative", context);
    }
}

