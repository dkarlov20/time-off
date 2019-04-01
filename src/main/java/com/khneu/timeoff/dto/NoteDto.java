package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private Integer id;
    private EmployeeDto employee;
    private String text;
    private String created;
}
