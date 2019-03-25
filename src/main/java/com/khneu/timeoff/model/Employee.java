package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Employee {
    private int id;
    private Position position;
    private String firstName;
    private String lastName;
    private String email;
}
