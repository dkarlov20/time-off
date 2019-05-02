package com.khneu.timeoff.mail.factory.impl;

import com.khneu.timeoff.mail.factory.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class ParentalLeaveMail implements Mail {
    private static final String SUBJECT = "Parental Leave";
    private static final String MESSAGE = " is going to have parental leave";

    @Override
    public SimpleMailMessage getMessage(String from, String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(from + MESSAGE);

        return message;
    }
}
