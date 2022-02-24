package com.encora.eci.controllers;

import com.encora.eci.entities.Employee;
import com.encora.eci.services.EmployeeService;
import com.encora.eci.util.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getEmployee(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String position
    ) {
        return employeeService.findEmployee(firstName, lastName, position);
    }

    @GetMapping("/employees/{employeeNumber}")
    public Employee getEmployee(@PathVariable Long employeeNumber/*, HttpServletResponse response*/) {
        Employee employee = employeeService.findById(employeeNumber);

        if (employee == null) {
            //response.setStatus(HttpStatus.NO_CONTENT.value());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND /*, ResponseMessages.EMPLOYEE_NOT_FOUND_MESSAGE.value()*/);
        }

        return employee;
    }

    @GetMapping("/employees/birthday")
    public Map<String, List<Employee>> getBirthdayInfo() {
        return employeeService.getBirthdayInfo();
    }

}
