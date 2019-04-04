package com.khneu.timeoff.specification;


import com.khneu.timeoff.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;

public class TimeOffRequestSpecification {
    public static Specification<TimeOffRequest> getTimeOffRequestById(Integer id) {
        return (Specification<TimeOffRequest>) (root, query, criteriaBuilder) -> id == null ?
                null : criteriaBuilder.equal(root.get(TimeOffRequest_.id), id);
    }

    public static Specification<TimeOffRequest> getTimeOffRequestByEmployeeId(Integer employeeId) {
        return (Specification<TimeOffRequest>) (root, query, criteriaBuilder) -> {
            if (employeeId == null) {
                return null;
            }

            Join<TimeOffRequest, Employee> requestEmployeeJoin = root.join(TimeOffRequest_.employee);
            return criteriaBuilder.equal(requestEmployeeJoin.get(Employee_.id), employeeId);
        };
    }

    public static Specification<TimeOffRequest> getTimeOffRequestByStart(LocalDate start) {
        return (Specification<TimeOffRequest>) (root, query, criteriaBuilder) -> start == null ?
                null : criteriaBuilder.greaterThanOrEqualTo(root.get(TimeOffRequest_.start), start);
    }

    public static Specification<TimeOffRequest> getTimeOffRequestByEnd(LocalDate end) {
        return (Specification<TimeOffRequest>) (root, query, criteriaBuilder) -> end == null ?
                null : criteriaBuilder.lessThanOrEqualTo(root.get(TimeOffRequest_.end), end);
    }

    public static Specification<TimeOffRequest> getTimeOffRequestByType(Type type) {
        return (Specification<TimeOffRequest>) (root, query, criteriaBuilder) -> {
            if (type == null) {
                return null;
            }

            Join<TimeOffRequest, RequestType> requestTypeJoin = root.join(TimeOffRequest_.requestType);
            return criteriaBuilder.equal(requestTypeJoin.get(RequestType_.type), type);
        };
    }

    public static Specification<TimeOffRequest> getTimeOffRequestByStatus(Status status) {
        return (Specification<TimeOffRequest>) (root, query, criteriaBuilder) -> {
            if (status == null) {
                return null;
            }

            Join<TimeOffRequest, CurrentRequestStatus> requestTypeJoin = root.join(TimeOffRequest_.currentRequestStatus);
            Join<CurrentRequestStatus, RequestStatus> currentStatusRequestStatusJoin = requestTypeJoin.join(CurrentRequestStatus_.requestStatus);
            return criteriaBuilder.equal(currentStatusRequestStatusJoin.get(RequestStatus_.status), status);
        };
    }
}