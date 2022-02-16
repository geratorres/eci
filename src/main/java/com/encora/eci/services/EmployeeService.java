package com.encora.eci.services;

import com.encora.eci.entities.Employee;

public interface EmployeeService {

    Employee findById(Long employeeNumber);
}
