package com.encora.eci.configuration;

import com.encora.eci.entities.Employee;
import com.encora.eci.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    //@Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        Employee employee;
        Employee employee2;
        Employee employee3;
        Employee employee4;
        Employee employee5;

        employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setState("Chihuahua");
        employee.setCountry("Mexico");
        employee.setAddress("1 washington st.");
        employee.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 1));
        employee.setCompanyEmail("jhon.smith@encora.com");
        employee.setPersonalEmail("jsmith@gmail.com");
        employee.setGender("MALE");
        employee.setPhoneNumber("111111111");

        employee2 = new Employee();
        employee2.setFirstName("John2");
        employee2.setLastName("Smith2");
        employee2.setState("Chihuahua");
        employee2.setCountry("Mexico");
        employee2.setAddress("2 washington st.");
        employee2.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 1));
        employee2.setCompanyEmail("jhon.smith2@encora.com");
        employee2.setPersonalEmail("jsmith2@gmail.com");
        employee2.setGender("MALE");
        employee2.setPhoneNumber("2222222");

        employee3 = new Employee();
        employee3.setFirstName("John3");
        employee3.setLastName("Smith3");
        employee3.setState("Chihuahua");
        employee3.setCountry("Mexico");
        employee3.setAddress("3 washington st.");
        employee3.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 2));
        employee3.setCompanyEmail("jhon.smith3@encora.com");
        employee3.setPersonalEmail("jsmith3@gmail.com");
        employee3.setGender("MALE");
        employee3.setPhoneNumber("33333333");

        employee4 = new Employee();
        employee4.setFirstName("John4");
        employee4.setLastName("Smith4");
        employee4.setState("Chihuahua");
        employee4.setCountry("Mexico");
        employee4.setAddress("123 washington st.");
        employee4.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 3));
        employee4.setCompanyEmail("jhon.smith4@encora.com");
        employee4.setPersonalEmail("jsmith4@gmail.com");
        employee4.setGender("MALE");
        employee4.setPhoneNumber("444444444");

        employee5 = new Employee();
        employee5.setFirstName("John5");
        employee5.setLastName("Smith5");
        employee5.setState("Chihuahua");
        employee5.setCountry("Mexico");
        employee5.setAddress("5 washington st.");
        employee5.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 3));
        employee5.setCompanyEmail("jhon.smith5@encora.com");
        employee5.setPersonalEmail("jsmith5@gmail.com");
        employee5.setGender("MALE");
        employee5.setPhoneNumber("5555555");

        List<Employee> employeeList = List.of(employee, employee2, employee3, employee4, employee5);

        return args -> employeeList.forEach((Employee emp) -> log.info("Preloading " + repository.save(emp)));
    }
}
