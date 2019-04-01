package com.khneu.timeoff.dto;

import com.khneu.timeoff.model.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeePositionDto {
    private Integer id;
    private Position position;
}
