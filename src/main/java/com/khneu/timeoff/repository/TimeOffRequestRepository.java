package com.khneu.timeoff.repository;

import com.khneu.timeoff.model.TimeOffRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TimeOffRequestRepository extends JpaRepository<TimeOffRequest, Integer>, JpaSpecificationExecutor<TimeOffRequest> {
}
