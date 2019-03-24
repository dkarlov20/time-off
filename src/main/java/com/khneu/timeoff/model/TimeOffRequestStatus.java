package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class TimeOffRequestStatus {
    private int id;
    private TimeOffRequest timeOffRequest;
    private Status status;
    private Employee employee;
    private LocalDateTime last_changed;
}
