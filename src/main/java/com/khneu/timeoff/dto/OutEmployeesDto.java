package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutEmployeesDto {
    private Integer timeOffRequestId;
    private EmployeeDto employee;
    private String start;
    private String end;
}
