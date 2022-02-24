package com.encora.eci.services;

import com.encora.eci.entities.Employee;
import com.encora.eci.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee findById(Long employeeNumber) {
        return null;
    }

    @Override
    public Map<String, List<Employee>> getBirthdayInfo() {
        return null;
    }

    @Override
    public List<Employee> findEmployee(String firstName, String lastName, String position) {

        return employeeRepository.findAll();
    }


}
