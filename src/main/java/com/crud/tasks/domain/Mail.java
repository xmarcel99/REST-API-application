package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mail {
    String receiverEmail;
    String subject;
    String message;
    String toCc;

    public Mail(String receiverEmail, String subject, String message) {
        this.receiverEmail = receiverEmail;
        this.subject = subject;
        this.message = message;
    }
}
