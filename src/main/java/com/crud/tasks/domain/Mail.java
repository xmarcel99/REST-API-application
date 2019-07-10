package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mail {
    final String receiverEmail;
    final String subject;
    final String message;
    final String toCc;
}
