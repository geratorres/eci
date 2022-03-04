package com.encora.eci.repositories;

import com.encora.eci.entities.Employee;
import com.encora.eci.entities.EmployeePositionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePositionHistoryRepository extends JpaRepository<EmployeePositionHistory, Long> {
    List<EmployeePositionHistory> findByEmployee(Employee employee);
}
