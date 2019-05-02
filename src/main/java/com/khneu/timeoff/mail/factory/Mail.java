package com.khneu.timeoff.mail.factory;

import org.springframework.mail.SimpleMailMessage;

public interface Mail {
    SimpleMailMessage getMessage(String from, String... to);
}
