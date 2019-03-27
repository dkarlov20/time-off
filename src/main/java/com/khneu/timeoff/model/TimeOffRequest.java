package com.khneu.timeoff.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"timeOffRequestStatuses"})
@EqualsAndHashCode(exclude = {"timeOffRequestStatuses"})
@Entity
@Table(name = "time_off_request")
public class TimeOffRequest implements Serializable {
    private static final long serialVersionUID = -7878230053424025932L;

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "request_type_id")
    private RequestType requestType;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "time_off_request_note",
            joinColumns = {@JoinColumn(name = "time_off_request_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "note_id", referencedColumnName = "id")})
    private Set<Note> notes;

    @OneToMany(mappedBy = "timeOffRequest", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TimeOffRequestStatus> timeOffRequestStatuses;

    private LocalDate start;

    private LocalDate end;

    private LocalDate created;
}
