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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Multimaps.index;
import static com.khneu.timeoff.specification.TimeOffRequestSpecification.*;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Comparator.comparing;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class TimeOffRequestServiceImpl implements TimeOffRequestService {
    private static final int DEFAULT_END_DAYS = 14;

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<TimeOffRequestDto> getTimeOffRequests(TimeOffRequestDto timeOffRequestDto) {
        TimeOffRequest timeOffRequest = mapper.toTimeOffRequest(timeOffRequestDto);

        List<TimeOffRequestDto> timeOffRequests = mapper.toTimeOffRequestsDto(timeOffRequestRepository.findAll(
                where(getTimeOffRequestById(timeOffRequest.getId()))
                        .and(getTimeOffRequestByEmployeeId(timeOffRequest.getEmployee().getId()))
                        .and(getTimeOffRequestByStart(timeOffRequest.getStart()))
                        .and(getTimeOffRequestByEnd(timeOffRequest.getEnd()))
                        .and(getTimeOffRequestByType(timeOffRequest.getRequestType().getType()))
                        .and(getTimeOffRequestByStatus(timeOffRequest.getCurrentRequestStatus().getRequestStatus().getStatus()))));

        for (TimeOffRequestDto t : timeOffRequests) {
            t.setDaysAmount(getTimeOffDaysAmount(t.getStart(), t.getEnd()));
        }

        return timeOffRequests;
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
        List<TimeOffRequest> timeOffRequests = timeOffRequestRepository.findAll(
                where(getTimeOffRequestByEmployeeId(employeeId))
                        .and(getTimeOffRequestByStatus(Status.APPROVED))
                        .and(where(getTimeOffRequestByType(Type.FULLY_PAID_SICK_LEAVE))
                                .or(getTimeOffRequestByType(Type.VACATION))));

        return timeOffRequests.size() == 0 ? estimateEmptyTimeOff(end) : estimateUsedTimeOff(end, timeOffRequests);
    }

    @Override
    public List<OutEmployeesDto> getOutEmployees(LocalDate start, LocalDate end) {
        List<OutEmployeesDto> outEmployees = new ArrayList<>();

        if (end == null) {
            end = start.plusDays(DEFAULT_END_DAYS);
        }

        List<TimeOffRequest> timeOffRequests = timeOffRequestRepository.findAll(
                where(getTimeOffRequestByStatus(Status.APPROVED))
                        .and(getTimeOffRequestByStart(start))
                        .and(getTimeOffRequestByEnd(end)));

        timeOffRequests.forEach(timeOffRequest -> outEmployees.add(OutEmployeesDto.builder()
                .timeOffRequestId(timeOffRequest.getId())
                .employee(mapper.toEmployeeDto(timeOffRequest.getEmployee()))
                .start(timeOffRequest.getStart().toString())
                .end(timeOffRequest.getEnd().toString())
                .build()));

        outEmployees.sort(comparing(OutEmployeesDto::getStart));

        return outEmployees;
    }

    @Override
    public List<TimeOffBalanceDto> getTimeOffBalance(int employeeId) {
        Multimap<RequestType, TimeOffRequest> timeOffRequestMultimap = index(
                timeOffRequestRepository.findAll(
                        where(getTimeOffRequestByEmployeeId(employeeId))
                                .and(getTimeOffRequestByStatus(Status.APPROVED))), TimeOffRequest::getRequestType);

        return requestTypeRepository.findAll().stream()
                .map(requestType -> TimeOffBalanceDto.builder()
                        .requestType(mapper.toRequestTypeDto(requestType))
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
        return timeOffRequests.stream()
                .mapToInt(value -> getTimeOffDaysAmount(value.getStart(), value.getEnd()))
                .sum();
    }

    private float countAvailableDays(Collection<TimeOffRequest> timeOffRequests, Type type) {
        if (type == Type.VACATION || type == Type.FULLY_PAID_SICK_LEAVE) {
            int usedDays = countUsedDays(timeOffRequests);

            return type == Type.VACATION ?
                    estimatePossibleVacationBalance(LocalDate.now()) - usedDays
                    :
                    TimeOffAccrual.FULLY_PAID_SICK_LEAVE - usedDays;
        }

        return 0;
    }

    private float estimatePossibleVacationBalance(LocalDate end) {
        return end.equals(end.with(TemporalAdjusters.lastDayOfMonth())) ?
                end.getMonthValue() * TimeOffAccrual.VACATION : (end.getMonthValue() - 1) * TimeOffAccrual.VACATION;
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
