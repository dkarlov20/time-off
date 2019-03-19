package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class TimeOffDate {
    private int id;
    private LocalDateTime ymd;
    private int amount;
}
