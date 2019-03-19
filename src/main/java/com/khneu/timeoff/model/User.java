package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class User {
    private int id;
    private Employee employee;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastLogin;
    private boolean isLoggedIn;
}
