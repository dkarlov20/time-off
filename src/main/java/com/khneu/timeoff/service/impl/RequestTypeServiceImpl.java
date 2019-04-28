package com.khneu.timeoff.service.impl;

import com.khneu.timeoff.dto.RequestTypeDto;
import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.model.Type;
import com.khneu.timeoff.repository.RequestTypeRepository;
import com.khneu.timeoff.service.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestTypeServiceImpl implements RequestTypeService {

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public RequestTypeDto getByType(Type type) {
        return mapper.toRequestTypeDto(requestTypeRepository.findByType(type));
    }
}
