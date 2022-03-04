package com.encora.eci.controllers;

import com.encora.eci.dtos.EmployeeHistoryDto;
import com.encora.eci.entities.Employee;
import com.encora.eci.services.EmployeeAdminService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping(value = "/admin/employees")
@RestController
public class EmployeeAdminController {

    private EmployeeAdminService employeeAdminService;

    public EmployeeAdminController(EmployeeAdminService employeeAdminService) {
        this.employeeAdminService = employeeAdminService;
    }

    @PostMapping
    public Employee post(@Valid @RequestBody Employee newEmployee) {

        return employeeAdminService.create(newEmployee);
    }

    @PostMapping("/{employeeId}/employee-position/{positionId}")
    public Employee setPosition(@PathVariable long employeeId, @PathVariable long positionId, @RequestBody double salary){
        return employeeAdminService.setPositionAndSalary(employeeId, positionId, salary);
    }

    @DeleteMapping("/{employeeId}")
    public Employee delete(@PathVariable long employeeId){
        return employeeAdminService.inactivateEmployee(employeeId);
    }

    @GetMapping("/search")
    public List<Employee> search( @RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String positionName
    ) {
        return employeeAdminService.search(firstName, lastName, positionName);
    }


    @GetMapping("/{employeeId}")
    public EmployeeHistoryDto one(@PathVariable long employeeId){
        return employeeAdminService.getEmployeeHistory(employeeId);
    }
}
