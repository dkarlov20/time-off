package com.khneu.timeoff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RequestType implements Serializable {
    private static final long serialVersionUID = 4026223904817750665L;

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    private Type type;
}
