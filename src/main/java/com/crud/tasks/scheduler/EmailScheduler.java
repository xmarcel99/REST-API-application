package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static String SUBJECT = "Task: Once a day email";

    @Autowired
    SimpleEmailService simpleEmailService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(),
                SUBJECT,
                generateMessage(taskRepository.count())
        ));
    }

    public String generateMessage(long count) {
        String taskOrTasks = "";
        if (count > 1 || count == 0) {
            taskOrTasks = "tasks";
        } else if (count == 1) {
            taskOrTasks = "task";
        }
        String message = "Currently in database you got: " + count + " " + taskOrTasks;
        return message;
    }
}
