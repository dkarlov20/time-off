package com.khneu.timeoff.repository;

import com.khneu.timeoff.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
