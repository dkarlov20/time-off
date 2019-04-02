package com.khneu.timeoff.service;

import com.khneu.timeoff.dto.CurrentRequestStatusDto;
import com.khneu.timeoff.dto.TimeOffRequestDto;

import java.util.List;

public interface TimeOffRequestService {
    List<TimeOffRequestDto> getTimeOffRequests(TimeOffRequestDto timeOffRequest);

    TimeOffRequestDto saveTimeOffRequest(TimeOffRequestDto timeOffRequest);

    TimeOffRequestDto changeTimeOffRequestStatus(int timeOffRequestId, CurrentRequestStatusDto currentRequestStatus);
}
