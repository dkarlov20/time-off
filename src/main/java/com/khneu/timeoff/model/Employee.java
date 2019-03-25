package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Employee {
    private int id;
    private EmployeePosition employeePosition;
    private String firstName;
    private String lastName;
    private String email;
}
