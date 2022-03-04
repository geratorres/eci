package com.encora.eci.controllers;

import com.encora.eci.dtos.reports.SalaryAndGenderReportDto;
import com.encora.eci.services.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/reports")
public class AdminReportController {
    private ReportService reportService;

    public AdminReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/position-employee-count")
    public Map<String, Integer> positionEmployeeCountReport() {
        return reportService.getPositionEmployeeCount();
    }

    @GetMapping("/gender-and-salaries")
    public SalaryAndGenderReportDto genderAndSalaries() {
        return reportService.getSalariesAndGender();
    }


    @GetMapping("/employees-by-location")
    public Map<String, Map<String, Integer>> locationReport() {
        return reportService.getLocationReport();
    }

}
