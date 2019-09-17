package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    @Autowired
    MailCreatorService mailCreatorService;

    public static Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(Mail mail) {
        LOGGER.info("Starting sending email...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been sent !");
        } catch (MailException e) {
            LOGGER.error("Fail to process email sending: ", e.getMessage(), e);
        }
    }

    public void sendEmailScheduler(Mail mail) {
        LOGGER.info("Starting sending email...");
        try {
            javaMailSender.send(sendScheduler(mail));
            LOGGER.info("Email has been sent !");
        } catch (MailException e) {
            LOGGER.error("Fail to process email sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(Mail mail) {
        return  mimeMessage ->
        {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getReceiverEmail());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        };
    }

    private MimeMessagePreparator sendScheduler(Mail mail) {
        return  mimeMessage ->
        {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getReceiverEmail());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.dbCoinditionInformations(mail.getMessage()));
        };
    }
}
