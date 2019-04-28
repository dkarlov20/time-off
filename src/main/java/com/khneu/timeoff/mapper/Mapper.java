package com.khneu.timeoff.mapper;

import com.khneu.timeoff.dto.*;
import com.khneu.timeoff.model.*;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    List<TimeOffRequestDto> toTimeOffRequestsDto(List<TimeOffRequest> timeOffRequest);

    TimeOffRequestDto toTimeOffRequestDto(TimeOffRequest timeOffRequest);

    TimeOffRequest toTimeOffRequest(TimeOffRequestDto timeOffRequestDto);

    CurrentRequestStatus toCurrentRequestStatus(CurrentRequestStatusDto currentRequestStatusDto);

    EmployeeDto toEmployeeDto(Employee employee);

    RequestStatusDto toRequestStatusDto(RequestStatus requestStatus);

    RequestTypeDto toRequestTypeDto(RequestType requestType);
}
