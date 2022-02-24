package com.encora.eci.controllers;

import com.encora.eci.entities.Employee;
import com.encora.eci.services.EmployeeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping(value = "/admin/employees")
@RestController
public class EmployeeAdminController {

    @Autowired
    private EmployeeAdminService employeeAdminService;

    @PostMapping
    public Employee postEmployee(@Valid @RequestBody Employee newEmployee) {

        return employeeAdminService.saveEmployee(newEmployee);
    }


}
