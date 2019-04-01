package com.khneu.timeoff.dto;

import com.khneu.timeoff.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestStatusDto {
    private Integer id;
    private Status status;
}
