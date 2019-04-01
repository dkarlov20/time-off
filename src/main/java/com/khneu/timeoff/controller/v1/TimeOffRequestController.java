package com.khneu.timeoff.controller.v1;

import com.khneu.timeoff.dto.*;
import com.khneu.timeoff.model.Status;
import com.khneu.timeoff.model.Type;
import com.khneu.timeoff.service.TimeOffRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Path("/v1/time_off/requests")
public class TimeOffRequestController {

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TimeOffRequestDto> getTimeOffRequests(@QueryParam("id") Integer id,
                                                      @QueryParam("employeeId") Integer employeeId,
                                                      @QueryParam("start") String start,
                                                      @QueryParam("end") String end,
                                                      @QueryParam("type") Type type,
                                                      @QueryParam("status") Status status) {

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
}
