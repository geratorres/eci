package com.encora.eci.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class EmployeePositionHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee")
    @JsonIgnore
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee_position")
    private EmployeePosition employeePosition;

    private double salary;

    private LocalDate date;

    public EmployeePositionHistory(Employee employee, EmployeePosition employeePosition, double salary) {
        this.employee = employee;
        this.employeePosition = employeePosition;
        this.salary = salary;
        this.date = LocalDate.now();
    }
}
