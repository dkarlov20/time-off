package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private Integer id;
    private EmployeeDto employee;
    private String text;
    private LocalDateTime created;
}
