package com.encora.eci.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class EmployeePosition implements Serializable {

    @Id
    private long employeeNumber;
    @Id
    private String positionName;
    @Id
    private Date date;
}
