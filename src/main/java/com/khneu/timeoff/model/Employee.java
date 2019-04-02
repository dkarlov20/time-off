package com.khneu.timeoff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee implements Serializable {
    private static final long serialVersionUID = -6084597479346104528L;

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_position_id")
    private EmployeePosition employeePosition;

    private String firstName;

    private String lastName;

    private String email;
}
