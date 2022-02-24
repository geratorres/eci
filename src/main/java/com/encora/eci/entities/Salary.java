package com.encora.eci.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
public class Salary implements Serializable {

    @Id
    private long EmployeeNumber;
    @Id
    private double salary;
    @Id
    private LocalDate date;
}
