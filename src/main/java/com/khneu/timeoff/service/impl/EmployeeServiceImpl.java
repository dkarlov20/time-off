package com.khneu.timeoff.service.impl;

import com.khneu.timeoff.dto.EmployeeDto;
import com.khneu.timeoff.exception.NoSuchEntityException;
import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.repository.EmployeeRepository;
import com.khneu.timeoff.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public EmployeeDto getById(int id) {
        return mapper.toEmployeeDto(employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("No Employee entity was found with id = " + id)));
    }
}
