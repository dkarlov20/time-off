package com.khneu.timeoff.service.impl;

import com.khneu.timeoff.dto.CurrentRequestStatusDto;
import com.khneu.timeoff.dto.EstimateDto;
import com.khneu.timeoff.dto.OutEmployeesDto;
import com.khneu.timeoff.dto.TimeOffRequestDto;
import com.khneu.timeoff.exception.NoSuchEntityException;
import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.model.CurrentRequestStatus;
import com.khneu.timeoff.model.Status;
import com.khneu.timeoff.model.TimeOffRequest;
import com.khneu.timeoff.model.Type;
import com.khneu.timeoff.repository.TimeOffRequestRepository;
import com.khneu.timeoff.service.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.khneu.timeoff.constant.TimeOffAccrual.FULLY_PAID_SICK_LEAVE;
import static com.khneu.timeoff.constant.TimeOffAccrual.VACATION;
import static com.khneu.timeoff.specification.TimeOffRequestSpecification.*;
import static java.time.temporal.ChronoUnit.DAYS;

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

    private EstimateDto estimateUsedTimeOff(LocalDate end, List<TimeOffRequest> timeOffRequests) {
        int usedVacationDays = countUsedDays(filterTimeOffListByType(timeOffRequests, Type.VACATION));
        int usedSickLeaveDays = countUsedDays(filterTimeOffListByType(timeOffRequests, Type.FULLY_PAID_SICK_LEAVE));

        return new EstimateDto(estimatePossibleVacationBalance(end) - usedVacationDays,
                FULLY_PAID_SICK_LEAVE - usedSickLeaveDays);
    }

    private EstimateDto estimateEmptyTimeOff(LocalDate end) {
        return new EstimateDto(estimatePossibleVacationBalance(end), FULLY_PAID_SICK_LEAVE);
    }

    private int countUsedDays(List<TimeOffRequest> timeOffRequests) {
        int usedDays = 0;
        for (TimeOffRequest timeOffRequest : timeOffRequests) {
            usedDays += getTimeOffDaysAmount(timeOffRequest.getStart(), timeOffRequest.getEnd());
        }

        return usedDays;
    }

    private List<TimeOffRequest> filterTimeOffListByType(List<TimeOffRequest> timeOffRequests, Type type) {
        return timeOffRequests
                .stream()
                .filter(timeOffRequest -> timeOffRequest.getRequestType().getType() == type)
                .collect(Collectors.toList());
    }

    private float estimatePossibleVacationBalance(LocalDate end) {
        return end.equals(end.with(TemporalAdjusters.lastDayOfMonth())) ?
                end.getMonthValue() * VACATION : (end.getMonthValue() - 1) * VACATION;
    }

    private int getTimeOffDaysAmount(LocalDate start, LocalDate end) {
        return (int) DAYS.between(start, end);
    }
}
