package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateDto {
    private Float vacationBalance;
    private Integer sickLeaveBalance;
}
