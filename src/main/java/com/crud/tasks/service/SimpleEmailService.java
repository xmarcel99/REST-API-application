package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    public static Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(Mail mail) {
        LOGGER.info("Starting sending email...");
        try {
            SimpleMailMessage mailMessage = createMainMessage(mail);
            javaMailSender.send(mailMessage);
            LOGGER.info("Email has been sent !");
        } catch (MailException e) {
            LOGGER.error("Fail to process email sending: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMainMessage(Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getReceiverEmail());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (mail.getToCc() != null) {
            mailMessage.setCc(mail.getToCc());
        }
        return mailMessage;
    }
}
