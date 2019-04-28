package com.khneu.timeoff.service.impl;

import com.khneu.timeoff.dto.RequestStatusDto;
import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.model.Status;
import com.khneu.timeoff.repository.RequestStatusRepository;
import com.khneu.timeoff.service.RequestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestStatusServiceImpl implements RequestStatusService {

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public RequestStatusDto getByStatus(Status status) {
        return mapper.toRequestStatusDto(requestStatusRepository.findByStatus(status));
    }
}
