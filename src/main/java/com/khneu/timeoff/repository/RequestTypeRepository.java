package com.khneu.timeoff.repository;

import com.khneu.timeoff.model.RequestType;
import com.khneu.timeoff.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestTypeRepository extends JpaRepository<RequestType, Integer>, JpaSpecificationExecutor<RequestType> {
    RequestType findByType(Type type);
}
