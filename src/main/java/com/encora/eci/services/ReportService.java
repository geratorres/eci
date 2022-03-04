package com.encora.eci.services;

import com.encora.eci.dtos.reports.SalaryAndGenderReportDto;

import java.util.Map;

public interface ReportService {

    Map<String, Integer> getPositionEmployeeCount();

    SalaryAndGenderReportDto getSalariesAndGender();

    Map<String, Map<String, Integer>> getLocationReport();
}
