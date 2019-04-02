package com.khneu.timeoff.service.impl;

import com.khneu.timeoff.dto.CurrentRequestStatusDto;
import com.khneu.timeoff.dto.TimeOffRequestDto;
import com.khneu.timeoff.exception.NoSuchEntityException;
import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.model.CurrentRequestStatus;
import com.khneu.timeoff.model.TimeOffRequest;
import com.khneu.timeoff.repository.TimeOffRequestRepository;
import com.khneu.timeoff.service.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.khneu.timeoff.specification.TimeOffRequestSpecification.*;

@Service
public class TimeOffRequestServiceImpl implements TimeOffRequestService {

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<TimeOffRequestDto> getTimeOffRequests(TimeOffRequestDto timeOffRequestDto) {
        TimeOffRequest timeOffRequest = mapper.toTimeOffRequest(timeOffRequestDto);

        return mapper.toTimeOffRequestsDto(timeOffRequestRepository.findAll(Specification.where(getTimeOffRequestById(timeOffRequest.getId()))
                .and(getTimeOffRequestByEmployeeId(timeOffRequest.getEmployee().getId()))
                .and(getTimeOffRequestByStart(timeOffRequest.getStart()))
                .and(getTimeOffRequestByEnd(timeOffRequest.getEnd()))
                .and(getTimeOffRequestByType(timeOffRequest.getRequestType().getType()))
                .and(getTimeOffRequestByStatus(timeOffRequest.getCurrentRequestStatus().getRequestStatus().getStatus()))));
    }

    @Override
    public TimeOffRequestDto saveTimeOffRequest(TimeOffRequestDto timeOffRequest) {
        return mapper.toTimeOffRequestDto(timeOffRequestRepository.save(mapper.toTimeOffRequest(timeOffRequest)));
    }

    @Override
    public TimeOffRequestDto changeTimeOffRequestStatus(int timeOffRequestId, CurrentRequestStatusDto currentRequestStatusDto) {
        TimeOffRequest timeOffRequest = timeOffRequestRepository.findById(timeOffRequestId)
                .orElseThrow(() -> new NoSuchEntityException("No TimeOffRequest entity was found with id = " + timeOffRequestId));
        CurrentRequestStatus currentRequestStatus = mapper.toCurrentRequestStatus(currentRequestStatusDto);

        timeOffRequest.getCurrentRequestStatus().setEmployee(currentRequestStatus.getEmployee());
        timeOffRequest.getCurrentRequestStatus().setRequestStatus(currentRequestStatus.getRequestStatus());
        timeOffRequest.getCurrentRequestStatus().setLastChanged(LocalDateTime.now());

        return mapper.toTimeOffRequestDto(timeOffRequestRepository.save(timeOffRequest));
    }
}
