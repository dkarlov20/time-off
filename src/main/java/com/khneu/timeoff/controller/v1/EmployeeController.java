package com.khneu.timeoff.controller.v1;

import com.khneu.timeoff.dto.EmployeeDto;
import com.khneu.timeoff.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable Integer id) {
        return employeeService.getById(id);
    }
}
