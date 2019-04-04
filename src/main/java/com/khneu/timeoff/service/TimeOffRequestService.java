package com.khneu.timeoff.service;

import com.khneu.timeoff.dto.CurrentRequestStatusDto;
import com.khneu.timeoff.dto.EstimateDto;
import com.khneu.timeoff.dto.OutEmployeesDto;
import com.khneu.timeoff.dto.TimeOffRequestDto;

import java.time.LocalDate;
import java.util.List;

public interface TimeOffRequestService {
    List<TimeOffRequestDto> getTimeOffRequests(TimeOffRequestDto timeOffRequest);

    TimeOffRequestDto saveTimeOffRequest(TimeOffRequestDto timeOffRequest);

    TimeOffRequestDto changeTimeOffRequestStatus(int timeOffRequestId, CurrentRequestStatusDto currentRequestStatus);

    EstimateDto estimateTimeOff(int employeeId, LocalDate end);

    List<OutEmployeesDto> getOutEmployees(LocalDate start, LocalDate end);
}
