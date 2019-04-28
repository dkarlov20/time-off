package com.khneu.timeoff.controller.v1;

import com.khneu.timeoff.dto.RequestTypeDto;
import com.khneu.timeoff.model.Type;
import com.khneu.timeoff.service.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/request_types")
public class RequestTypeController {

    @Autowired
    private RequestTypeService requestTypeService;

    @GetMapping
    public RequestTypeDto getRequestTypeByType(@RequestParam("type") Type type) {
        return requestTypeService.getByType(type);
    }
}
