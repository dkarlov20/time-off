package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeOffBalanceDto {
    private RequestTypeDto requestType;
    private Integer usedDays;
    private Float availableDays;
}
