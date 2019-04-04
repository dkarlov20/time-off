package com.khneu.timeoff.mapper;

import com.khneu.timeoff.dto.CurrentRequestStatusDto;
import com.khneu.timeoff.dto.EmployeeDto;
import com.khneu.timeoff.dto.TimeOffRequestDto;
import com.khneu.timeoff.model.CurrentRequestStatus;
import com.khneu.timeoff.model.Employee;
import com.khneu.timeoff.model.TimeOffRequest;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    List<TimeOffRequestDto> toTimeOffRequestsDto(List<TimeOffRequest> timeOffRequest);

    TimeOffRequestDto toTimeOffRequestDto(TimeOffRequest timeOffRequest);

    TimeOffRequest toTimeOffRequest(TimeOffRequestDto timeOffRequestDto);

    CurrentRequestStatus toCurrentRequestStatus(CurrentRequestStatusDto currentRequestStatusDto);

    EmployeeDto toEmployeeDto(Employee employee);
}
