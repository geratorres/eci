package com.encora.eci.controllers;

import com.encora.eci.entities.Employee;
import com.encora.eci.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController //indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
public class EmployeeController {

    public static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee Not Found";
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
            this.employeeService = employeeService;
    }

    @GetMapping("/employee/{employeeNumber}")
    public Employee getEmployee(@PathVariable Long employeeNumber){
        Employee employee = employeeService.findById(employeeNumber);

        if(employee == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, EMPLOYEE_NOT_FOUND_MESSAGE);
        }

        return employee;
    }

}
