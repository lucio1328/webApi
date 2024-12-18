package com.web.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailTemplateService {

    @Autowired
    private TemplateEngine templateEngine;

    public String generateEmailContent(String name) {
        Context context = new Context();
        context.setVariable("name", name);

        return templateEngine.process("emailTemplate", context);
    }
}

