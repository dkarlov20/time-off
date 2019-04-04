package com.khneu.timeoff.controller.v1;

import com.khneu.timeoff.dto.*;
import com.khneu.timeoff.model.Status;
import com.khneu.timeoff.model.Type;
import com.khneu.timeoff.service.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/time_off")
public class TimeOffRequestController {

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @GetMapping("/requests")
    public List<TimeOffRequestDto> getTimeOffRequests(@RequestParam(value = "id", required = false) Integer id,
                                                      @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                                      @RequestParam(value = "start", required = false) String start,
                                                      @RequestParam(value = "end", required = false) String end,
                                                      @RequestParam(value = "type", required = false) Type type,
                                                      @RequestParam(value = "status", required = false) Status status) {

        TimeOffRequestDto timeOffRequest = TimeOffRequestDto.builder()
                .id(id)
                .employee(EmployeeDto.builder()
                        .id(employeeId).build())
                .start(start)
                .end(end)
                .requestType(RequestTypeDto.builder()
                        .type(type).build())
                .currentRequestStatus(CurrentRequestStatusDto.builder()
                        .requestStatus(RequestStatusDto.builder()
                                .status(status).build()).build())
                .build();

        return timeOffRequestService.getTimeOffRequests(timeOffRequest);
    }

    @PutMapping("/request")
    public TimeOffRequestDto saveTimeOffRequest(@RequestBody TimeOffRequestDto timeOffRequest) {
        return timeOffRequestService.saveTimeOffRequest(timeOffRequest);
    }

    @PutMapping("/requests/{id}/status")
    public TimeOffRequestDto changeTimeOffRequestStatus(@PathVariable Integer id,
                                                        @RequestBody CurrentRequestStatusDto currentRequestStatus) {

        return timeOffRequestService.changeTimeOffRequestStatus(id, currentRequestStatus);
    }

    @GetMapping("/estimate")
    public EstimateDto estimateTimeOff(@RequestParam(value = "employeeId") Integer employeeId,
                                       @RequestParam(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return timeOffRequestService.estimateTimeOff(employeeId, end);
    }
}
