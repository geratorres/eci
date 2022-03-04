package com.encora.eci.services;

import com.encora.eci.dtos.EmployeeHistoryDto;
import com.encora.eci.entities.Employee;
import com.encora.eci.exceptions.inner.EmployeeRequiredException;

import java.util.List;

public interface EmployeeAdminService {
    Employee create(Employee employee) throws EmployeeRequiredException;

    Employee inactivateEmployee(long employeeNumber);

    Employee setPositionAndSalary(long employeeId, long employeePositionId, double salary);

    Employee findById(long employeeNumber);

    List<Employee> search(String firstName, String lastName, String positionName);

    EmployeeHistoryDto getEmployeeHistory(long id);

}
