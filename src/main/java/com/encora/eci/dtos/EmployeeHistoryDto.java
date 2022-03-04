package com.encora.eci.dtos;

import com.encora.eci.entities.Employee;
import com.encora.eci.entities.EmployeePositionHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeHistoryDto {
    private Employee employee;
    private List<EmployeePositionHistory> history;
}
