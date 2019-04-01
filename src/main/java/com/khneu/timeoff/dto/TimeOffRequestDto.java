package com.khneu.timeoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeOffRequestDto {
    private Integer id;
    private EmployeeDto employee;
    private CurrentRequestStatusDto currentRequestStatus;
    private String start;
    private String end;
    private String created;
    private RequestTypeDto requestType;
    private Set<NoteDto> notes;
}
