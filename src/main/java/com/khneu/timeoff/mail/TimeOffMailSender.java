package com.khneu.timeoff.mail;

import com.khneu.timeoff.exception.BusinessException;
import com.khneu.timeoff.mail.factory.Mail;
import com.khneu.timeoff.mail.factory.MailFactory;
import com.khneu.timeoff.model.TimeOffRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class TimeOffMailSender {
    private static final String TO = "d.karlov20@gmail.com";

    @Autowired
    private MailFactory mailFactory;

    @Autowired
    private JavaMailSender mailSender;

    public void send(TimeOffRequest timeOffRequest) {
        Mail mail = mailFactory.create(timeOffRequest.getRequestType().getType())
                .orElseThrow(() -> new BusinessException("Cannot create Mail instance from given request type"));

        mailSender.send(mail.getMessage(timeOffRequest.getEmployee().getEmail(), TO));
    }
}
