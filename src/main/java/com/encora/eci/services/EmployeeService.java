package com.encora.eci.services;

import com.encora.eci.entities.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    Employee findById(Long employeeNumber);
    Map<String, List<Employee>> getBirthdayInfo();
    List<Employee> findEmployee(String firstName, String lastName, String position);
}
