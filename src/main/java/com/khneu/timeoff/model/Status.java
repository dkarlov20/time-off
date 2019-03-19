package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class Status {
    private int id;
    private LocalDateTime lastChanged;
    private User lastChangedBy;
    private String name;
}
