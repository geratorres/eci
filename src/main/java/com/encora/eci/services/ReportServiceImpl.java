package com.encora.eci.services;

import com.encora.eci.dtos.reports.SalaryAndGenderReportDto;
import com.encora.eci.repositories.EmployeePositionRepository;
import com.encora.eci.repositories.EmployeeRepository;
import com.encora.eci.repositories.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private EmployeeRepository employeeRepository;
    private EmployeePositionRepository employeePositionRepository;
    private ReportRepository reportRepository;

    public ReportServiceImpl(EmployeeRepository employeeRepository, EmployeePositionRepository employeePositionRepository, ReportRepository reportRepository) {
        this.employeeRepository = employeeRepository;
        this.employeePositionRepository = employeePositionRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public Map<String, Integer> getPositionEmployeeCount() {
        return reportRepository.getEmployeeCountPerPosition();
    }

    @Override
    public SalaryAndGenderReportDto getSalariesAndGender() {
        SalaryAndGenderReportDto report = new SalaryAndGenderReportDto();

        Map<String, Integer> genderCount = reportRepository.getGenderCount();
        int employeeCount = 0;

        for (Map.Entry<String, Integer> entry : genderCount.entrySet()) {
            employeeCount += entry.getValue();
        }

        Map<String, Float> genderPercentage = new HashMap<>();
        for (Map.Entry<String, Integer> entry : genderCount.entrySet()) {
            genderPercentage.put(entry.getKey(), (float) entry.getValue() / employeeCount);
        }

        report.setGenderGraph(genderPercentage);
        report.setSalaryGraph(reportRepository.getSalaryRanges());
        report.setEmployeeCount(employeeCount);

        return report;
    }

    @Override
    public Map<String, Map<String, Integer>> getLocationReport() {
        return reportRepository.getEmployeesByLocation();
    }
}
