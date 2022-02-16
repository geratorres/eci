package com.encora.eci.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Employee{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long employeeNumber;
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String companyEmail;
    private String phoneNumber;
    private String address;
    private String gender;
    private LocalDate birthday;
}
