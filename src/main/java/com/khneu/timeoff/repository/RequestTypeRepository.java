package com.khneu.timeoff.repository;

import com.khneu.timeoff.model.RequestType;
import com.khneu.timeoff.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestTypeRepository extends JpaRepository<RequestType, Integer> {
    RequestType findByType(Type type);
}
