package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentRequestStatusDto {
    private Integer id;
    private RequestStatusDto requestStatus;
    private EmployeeDto employee;
    private LocalDateTime lastChanged;
}
