package com.khneu.timeoff.mapper;

import com.khneu.timeoff.dto.TimeOffRequestDto;
import com.khneu.timeoff.model.TimeOffRequest;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    List<TimeOffRequestDto> toTimeOffRequestsDto(List<TimeOffRequest> timeOffRequest);

    TimeOffRequestDto toTimeOffRequestDto(TimeOffRequest timeOffRequest);

    TimeOffRequest toTimeOffRequest(TimeOffRequestDto timeOffRequestDto);
}
