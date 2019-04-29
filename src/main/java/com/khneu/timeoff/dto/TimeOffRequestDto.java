package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeOffRequestDto {
    private Integer id;
    private EmployeeDto employee;
    private CurrentRequestStatusDto currentRequestStatus;
    private LocalDate start;
    private LocalDate end;
    private Integer daysAmount;
    private LocalDate created;
    private RequestTypeDto requestType;
    private Set<NoteDto> notes;
}
