package com.khneu.timeoff.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class RequestType {
    private int id;
    private Type type;
}
