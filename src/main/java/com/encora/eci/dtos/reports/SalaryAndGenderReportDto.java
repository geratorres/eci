package com.encora.eci.dtos.reports;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class SalaryAndGenderReportDto {
    private Map<String, Float> genderGraph;
    private List<Object[]> salaryGraph;
    private Integer employeeCount;
}
