package com.khneu.timeoff.service;

import com.khneu.timeoff.dto.RequestStatusDto;
import com.khneu.timeoff.model.Status;

public interface RequestStatusService {
    RequestStatusDto getByStatus(Status status);
}
