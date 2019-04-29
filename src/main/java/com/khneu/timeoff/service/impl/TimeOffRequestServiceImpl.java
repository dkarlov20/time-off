package com.khneu.timeoff.service.impl;

import com.google.common.collect.Multimap;
import com.khneu.timeoff.constant.TimeOffAccrual;
import com.khneu.timeoff.dto.*;
import com.khneu.timeoff.exception.NoSuchEntityException;
import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.model.*;
import com.khneu.timeoff.repository.RequestTypeRepository;
import com.khneu.timeoff.repository.TimeOffRequestRepository;
import com.khneu.timeoff.service.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Multimaps.index;
import static com.khneu.timeoff.constant.TimeOffAccrual.VACATION;
import static com.khneu.timeoff.specification.TimeOffRequestSpecification.*;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

@Service
public class TimeOffRequestServiceImpl implements TimeOffRequestService {

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

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

    @Override
    public EstimateDto estimateTimeOff(int employeeId, LocalDate end) {
        List<TimeOffRequest> timeOffRequests = timeOffRequestRepository.findAll(Specification
                .where(getTimeOffRequestByEmployeeId(employeeId))
                .and(getTimeOffRequestByStatus(Status.APPROVED))
                .and(Specification.where(getTimeOffRequestByType(Type.FULLY_PAID_SICK_LEAVE))
                        .or(getTimeOffRequestByType(Type.VACATION))));

        return timeOffRequests.size() == 0 ? estimateEmptyTimeOff(end) : estimateUsedTimeOff(end, timeOffRequests);
    }

    @Override
    public List<OutEmployeesDto> getOutEmployees(LocalDate start, LocalDate end) {
        List<OutEmployeesDto> outEmployees = new ArrayList<>();

        List<TimeOffRequest> timeOffRequests = timeOffRequestRepository.findAll(Specification
                .where(getTimeOffRequestByStatus(Status.APPROVED))
                .and(getTimeOffRequestByStart(start))
                .and(getTimeOffRequestByEnd(end)));

        timeOffRequests.forEach(timeOffRequest -> outEmployees.add(OutEmployeesDto.builder()
                .timeOffRequestId(timeOffRequest.getId())
                .employee(mapper.toEmployeeDto(timeOffRequest.getEmployee()))
                .start(timeOffRequest.getStart().toString())
                .end(timeOffRequest.getEnd().toString())
                .build()));

        outEmployees.sort(Comparator.comparing(OutEmployeesDto::getStart));

        return outEmployees;
    }

    @Override
    public List<TimeOffBalanceDto> getTimeOffBalance(int employeeId) {
        Multimap<RequestType, TimeOffRequest> timeOffRequestMultimap = index(
                timeOffRequestRepository.findAll(Specification
                        .where(getTimeOffRequestByEmployeeId(employeeId))
                        .and(getTimeOffRequestByStatus(Status.APPROVED))),
                TimeOffRequest::getRequestType);

        return requestTypeRepository.findAll().stream()
                .map(requestType -> TimeOffBalanceDto.builder()
                        .requestTypeDto(mapper.toRequestTypeDto(requestType))
                        .usedDays(countUsedDays(timeOffRequestMultimap.get(requestType)))
                        .availableDays(countAvailableDays(timeOffRequestMultimap.get(requestType), requestType.getType()))
                        .build())
                .collect(Collectors.toList());
    }

    private EstimateDto estimateUsedTimeOff(LocalDate end, List<TimeOffRequest> timeOffRequests) {
        Multimap<Type, TimeOffRequest> timeOffRequestMultimap = index(timeOffRequests,
                timeOffRequest -> timeOffRequest.getRequestType().getType());

        int usedVacationDays = countUsedDays(timeOffRequestMultimap.get(Type.VACATION));
        int usedSickLeaveDays = countUsedDays(timeOffRequestMultimap.get(Type.FULLY_PAID_SICK_LEAVE));

        return new EstimateDto(estimatePossibleVacationBalance(end) - usedVacationDays,
                TimeOffAccrual.FULLY_PAID_SICK_LEAVE - usedSickLeaveDays);
    }

    private EstimateDto estimateEmptyTimeOff(LocalDate end) {
        return new EstimateDto(estimatePossibleVacationBalance(end), TimeOffAccrual.FULLY_PAID_SICK_LEAVE);
    }

    private int countUsedDays(Collection<TimeOffRequest> timeOffRequests) {
        int usedDays = 0;
        for (TimeOffRequest timeOffRequest : timeOffRequests) {
            usedDays += getTimeOffDaysAmount(timeOffRequest.getStart(), timeOffRequest.getEnd());
        }

        return usedDays;
    }

    private float countAvailableDays(Collection<TimeOffRequest> timeOffRequests, Type type) {
        if (type == Type.VACATION || type == Type.FULLY_PAID_SICK_LEAVE) {
            int usedDays = countUsedDays(timeOffRequests);

            return type == Type.VACATION ?
                    estimatePossibleVacationBalance(LocalDate.now()) - usedDays
                    :
                    (float) (TimeOffAccrual.FULLY_PAID_SICK_LEAVE - usedDays);
        } else {
            return 0;
        }
    }

    private float estimatePossibleVacationBalance(LocalDate end) {
        return end.equals(end.with(TemporalAdjusters.lastDayOfMonth())) ?
                end.getMonthValue() * VACATION : (end.getMonthValue() - 1) * VACATION;
    }

    private int getTimeOffDaysAmount(LocalDate start, LocalDate end) {
        int amount = 0;
        LocalDate date = start;

        while (!date.minusDays(1).equals(end)) {
            if (date.getDayOfWeek() != SATURDAY && date.getDayOfWeek() != SUNDAY) {
                amount++;
            }
            date = date.plusDays(1);
        }

        return amount;
    }
}
