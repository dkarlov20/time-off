package com.khneu.timeoff.controller;

import com.khneu.timeoff.dto.ExceptionDetails;
import com.khneu.timeoff.exception.NoSuchEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDetails handleNoSuchEntityException(NoSuchEntityException e) {
        return new ExceptionDetails(LocalDateTime.now().format(ISO_DATE_TIME), e.getMessage());
    }
}
