package com.khneu.timeoff.service;

import com.khneu.timeoff.dto.TimeOffRequestDto;

import java.util.List;

public interface TimeOffRequestService {
    List<TimeOffRequestDto> getTimeOffRequests(TimeOffRequestDto timeOffRequest);
}
