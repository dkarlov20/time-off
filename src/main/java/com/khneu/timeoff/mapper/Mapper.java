package com.khneu.timeoff.mapper;

import com.khneu.timeoff.dto.TimeOffRequestDto;
import com.khneu.timeoff.model.TimeOffRequest;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    List<TimeOffRequestDto> toTimeOffRequestDto(List<TimeOffRequest> timeOffRequest);

    TimeOffRequest toTimeOffRequest(TimeOffRequestDto timeOffRequestDto);
}
