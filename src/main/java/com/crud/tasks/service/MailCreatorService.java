package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyDetails;
import com.crud.tasks.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private DbService dbService;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyDetails companyDetails;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> funcionality = new ArrayList<>();
        funcionality.add("You can menage your task");
        funcionality.add("Provides connection with Trello Account");
        funcionality.add("Application allows sending task to Trello");

        Context context = new Context();
        context.setVariable("message",message);
        context.setVariable("tasks_url","https://xmarcel99.github.io/");
        context.setVariable("button","Visit Website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("goodbye","Thanks " + adminConfig.getAdminName() + " for good configuration of me :)");
        context.setVariable("company_name",companyDetails.getCompanyName());
        context.setVariable("preview_message","Hello");
        context.setVariable("show_button",false);
        context.setVariable("is_friend",false);
        context.setVariable("admin_config",adminConfig);
        context.setVariable("application_functionality",funcionality);
        return templateEngine.process("mail/created-trello-card-mail",context);
    }

    public String dbCoinditionInformations(String message) {
        List<Task>  tasks = new ArrayList<>();
        tasks.addAll(dbService.getAllTasks());

        Context context = new Context();
        context.setVariable("message_content","Task in your DB: " + dbService.getAllTasks().size() + message);
        context.setVariable("admin_config",adminConfig);
        context.setVariable("is_friend",false);
        context.setVariable("button","add/delete from DB");
        context.setVariable("tasks_url","https://xmarcel99.github.io/");
        context.setVariable("goodbye_message","See you tomorrow with fresh informations");
        context.setVariable("company_details",companyDetails.getCompanyName());
        context.setVariable("all_tasks",tasks);


        return templateEngine.process("mail/db-condition-informations)",context);
    }
}
