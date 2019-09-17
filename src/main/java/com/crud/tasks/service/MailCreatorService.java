package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyDetails companyDetails;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","https://xmarcel99.github.io/");
        context.setVariable("button","Visit Website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("goodbye","Thanks " + adminConfig.getAdminName() + " for good configuration of me :)");
        context.setVariable("company_name",companyDetails.getCompanyName());
        context.setVariable("preview_message","Hello");
        return templateEngine.process("mail/created-trello-card-mail",context);
    }
}
