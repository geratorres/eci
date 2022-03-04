package com.encora.eci.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 100, message = "First name length must be between 2 and 100 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 100, message = "Last name length must be between 2 and 100 characters.")
    private String lastName;

    @NotBlank(message = "Gender is required.")
    @Size(min = 1, max = 20, message = "Gender length must be between 2 and 20 characters.")
    private String gender;

    @NotBlank(message = "Address is required.")
    @Size(min = 2, max = 200, message = "Adress length must be between 2 and 200 characters.")
    private String address;

    @NotBlank(message = "State is required.")
    @Size(min = 2, max = 100, message = "State length must be between 2 and 100 characters.")
    private String state;

    @Size(min = 2, max = 100, message = "Country length must be between 2 and 100 characters.")
    @NotBlank(message = "Country is required.")
    private String country;

    @Email(message = "The company email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    @Column(unique = true)
    private String companyEmail;

    @Email(message = "The personal email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    @Size(min = 2, max = 100, message = "Personal email length must be between 2 and 100 characters.")
    private String personalEmail;

    @Size(min = 2, max = 100, message = "Phone number length must be between 2 and 100 characters.")
    private String phoneNumber;

    @Past(message = "The birthday must be in the past.")
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee_position")
    private EmployeePosition employeePosition;


    private double salary;

    private boolean inactive;
}
