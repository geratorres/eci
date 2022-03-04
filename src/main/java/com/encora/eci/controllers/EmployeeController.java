package com.encora.eci.controllers;

import com.encora.eci.dtos.EmployeeBasicDto;
import com.encora.eci.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/search")
    public List<EmployeeBasicDto> search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String positionName
    ) {
        return employeeService.search(firstName, lastName, positionName);
    }

    @GetMapping("/{id}")
    public EmployeeBasicDto one(@PathVariable Long id/*, HttpServletResponse response*/) {

        return employeeService.findById(id);
    }

    @GetMapping("/birthday-info")
    public Map<String, List<EmployeeBasicDto>> getBirthdayInfo() {
        return employeeService.getBirthdayInfo();
    }
}
