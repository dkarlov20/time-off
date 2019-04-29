package com.khneu.timeoff.repository;

import com.khneu.timeoff.model.RequestStatus;
import com.khneu.timeoff.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Integer> {
    RequestStatus findByStatus(Status status);
}
