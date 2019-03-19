package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Amount {
    private int id;
    private String unit;
    private int number;
}
