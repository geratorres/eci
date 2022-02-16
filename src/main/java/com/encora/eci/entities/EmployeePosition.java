package com.encora.eci.entities;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class EmployeePosition {

    private long employeeNumber;
    private String positionName;
    private float Salary;
    private Date promotionDate;

}
