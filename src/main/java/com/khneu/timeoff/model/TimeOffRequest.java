package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class TimeOffRequest {
    private int id;
    private Employee employee;
    private Status status;
    private Type type;
    private List<Note> notes;
    private List<TimeOffRequestStatus> timeOffRequestStatuses;
    private LocalDate start;
    private LocalDate end;
    private LocalDate created;
}
