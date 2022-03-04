package com.encora.eci.dtos;

import com.encora.eci.entities.Employee;
import com.encora.eci.entities.EmployeePosition;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EmployeeBasicDto {

    public static EmployeeBasicDto of(Employee e) {
        return new EmployeeBasicDto(e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getGender(),
                e.getAddress(),
                e.getState(),
                e.getCountry(),
                e.getCompanyEmail(),
                e.getPersonalEmail(),
                e.getPhoneNumber(),
                e.getBirthday(),
                e.getEmployeePosition());
    }

    public static List<EmployeeBasicDto> of (List<Employee> employees){
        return employees.stream().map(EmployeeBasicDto::of).collect(Collectors.toList());
    }

    private EmployeeBasicDto(long id, String firstName, String lastName, String gender, String address, String state,
                             String country, String companyEmail, String personalEmail, String phoneNumber,
                             LocalDate birthday, EmployeePosition employeePosition) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.state = state;
        this.country = country;
        this.companyEmail = companyEmail;
        this.personalEmail = personalEmail;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.employeePosition = employeePosition;
    }

    private long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String state;
    private String country;
    private String companyEmail;
    private String personalEmail;
    private String phoneNumber;
    private LocalDate birthday;
    private EmployeePosition employeePosition;
}
