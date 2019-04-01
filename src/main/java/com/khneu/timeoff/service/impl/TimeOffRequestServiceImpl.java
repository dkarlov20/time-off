package com.khneu.timeoff.service.impl;

import com.khneu.timeoff.dto.TimeOffRequestDto;
import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.repository.TimeOffRequestRepository;
import com.khneu.timeoff.service.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.khneu.timeoff.specification.TimeOffRequestSpecification.getSpecification;

@Service
public class TimeOffRequestServiceImpl implements TimeOffRequestService {

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<TimeOffRequestDto> getTimeOffRequests(TimeOffRequestDto timeOffRequest) {
        return mapper.toTimeOffRequestDto(timeOffRequestRepository.findAll(getSpecification(mapper.toTimeOffRequest(timeOffRequest))));
    }
}
