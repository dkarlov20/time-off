package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentRequestStatusDto {
    private Integer id;
    private RequestStatusDto requestStatus;
    private EmployeeDto employee;
    private String lastChanged;
}
