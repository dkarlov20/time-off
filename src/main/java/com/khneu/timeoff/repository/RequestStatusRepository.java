package com.khneu.timeoff.repository;

import com.khneu.timeoff.model.RequestStatus;
import com.khneu.timeoff.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Integer>, JpaSpecificationExecutor<RequestStatus> {
    RequestStatus findByStatus(Status status);
}
