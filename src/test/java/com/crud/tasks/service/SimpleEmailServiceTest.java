package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    SimpleEmailService simpleEmailService;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    public void shouldSendMail() {

        //Given
        Mail mail = new Mail("szturmarcel@gmail.com","Test mail","Test mail",null);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("szturmarcel@gmail.com");
        simpleMailMessage.setText("Test mail");
        simpleMailMessage.setSubject("Test mail");

        //When
        simpleEmailService.send(mail);

        //Then
        Mockito.verify(javaMailSender,times(1)).send(simpleMailMessage);
    }
}