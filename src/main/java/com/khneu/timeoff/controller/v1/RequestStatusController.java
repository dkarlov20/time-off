package com.khneu.timeoff.controller.v1;

import com.khneu.timeoff.dto.RequestStatusDto;
import com.khneu.timeoff.model.Status;
import com.khneu.timeoff.service.RequestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/request_statuses")
public class RequestStatusController {
    @Autowired
    private RequestStatusService requestStatusService;

    @GetMapping
    public RequestStatusDto getByStatus(@RequestParam("status") Status status) {
        return requestStatusService.getByStatus(status);
    }
}
