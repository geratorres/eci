package com.encora.eci.repositories;


import java.util.List;
import java.util.Map;

public interface ReportRepository {

    Map<String,Integer> getEmployeeCountPerPosition();
    Map<String, Integer> getGenderCount();
    List<Object[]> getSalaryRanges();
    Map<String, Map<String, Integer>> getEmployeesByLocation();
}
