package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class EmployeePosition {
    private int id;
    private Position position;
}
