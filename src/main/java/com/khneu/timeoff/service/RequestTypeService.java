package com.khneu.timeoff.service;

import com.khneu.timeoff.dto.RequestTypeDto;
import com.khneu.timeoff.model.Type;

public interface RequestTypeService {
    RequestTypeDto getByType(Type type);
}
