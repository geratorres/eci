package com.encora.eci.configuration;

import com.encora.eci.entities.Employee;
import com.encora.eci.entities.EmployeePosition;
import com.encora.eci.exceptions.inner.EmployeeRequiredException;
import com.encora.eci.repositories.EmployeePositionRepository;
import com.encora.eci.repositories.EmployeeRepository;
import com.encora.eci.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(name = "bootstrapping.data", havingValue = "true")
class LoadDatabase {

    private EmployeeRepository employeeRepository;
    private EmployeePositionRepository employeePositionRepository;

    private static List<Employee> createNEmployees(int count) {
        List<Employee> employeeList = new ArrayList<>(count);
        count = count % 32;

        for (int i = 1; i <= count; i++) {
            employeeList.add(new Employee(
                    i,
                    "John" + i,
                    "Smith" + i,
                    "Male",
                    i + " washington st.",
                    "Chihuahua",
                    "Mexico",
                    "jhon.smith" + i + "@encora.com",
                    "jsmith" + i + "@gmail.com",
                    String.valueOf(i * 11111111),
                    LocalDate.of(1993, Month.MARCH, i),
                    null, 0, false)
            );
        }

        return employeeList;
    }

    public LoadDatabase(EmployeeRepository employeeRepository, EmployeePositionRepository employeePositionRepository) {
        this.employeeRepository = employeeRepository;
        this.employeePositionRepository = employeePositionRepository;
    }

    @EventListener
    void initDatabase(ApplicationReadyEvent applicationReadyEvent) throws EmployeeRequiredException {

        if (employeeRepository.count() == 0) {

            log.info("Inserting positions");

            EmployeePosition developer = employeePositionRepository.save(new EmployeePosition("developer"));
            EmployeePosition qa = employeePositionRepository.save(new EmployeePosition("tester"));
            EmployeePosition finance = employeePositionRepository.save(new EmployeePosition("finance"));

            log.info("Inserting Developers");
            //;
            createNEmployees(10).forEach(e -> employeeRepository.save(e));
        }
    }
}
