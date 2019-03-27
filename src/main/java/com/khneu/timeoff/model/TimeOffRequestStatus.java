package com.khneu.timeoff.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TimeOffRequestStatus implements Serializable {
    private static final long serialVersionUID = 2354352477540872257L;

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "time_off_request_id")
    private TimeOffRequest timeOffRequest;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "request_status_id")
    private RequestStatus requestStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime lastChanged;
}
