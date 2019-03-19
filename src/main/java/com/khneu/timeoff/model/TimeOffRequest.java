package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class TimeOffRequest {
    private int id;
    private Employee employee;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime created;
    private Status status;
    private Type type;
    private Amount amount;
    private List<TimeOffDate> timeOffDates;
    private List<Note> notes;
}
