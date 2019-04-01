package com.khneu.timeoff.dto;

import com.khneu.timeoff.model.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestTypeDto {
    private Integer id;
    private Type type;
}
