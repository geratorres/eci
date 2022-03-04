package com.encora.eci.services;

import com.encora.eci.dtos.EmployeeBasicDto;
import com.encora.eci.entities.Employee;
import com.encora.eci.entities.EmployeePosition;
import com.encora.eci.exceptions.outter.EmployeeNotFoundException;
import com.encora.eci.repositories.EmployeePositionRepository;
import com.encora.eci.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;
    private EmployeePositionRepository employeePositionRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeePositionRepository employeePositionRepository) {
        this.employeeRepository = employeeRepository;
        this.employeePositionRepository = employeePositionRepository;
    }

    @Override
    public EmployeeBasicDto findById(long employeeNumber) {
        Employee employee = employeeRepository.findByIdAndInactiveIs(employeeNumber, false);

        if (employee == null) throw new EmployeeNotFoundException();

        return EmployeeBasicDto.of(employee);
    }

    @Override
    public Map<String, List<EmployeeBasicDto>> getBirthdayInfo() {
        Map<String, List<EmployeeBasicDto>> map = Map.of("today", new LinkedList<>(), "nextWeek", new LinkedList<>());
        LocalDate now = LocalDate.now();
        final LocalDate date = LocalDate.of(2020, now.getMonth(), now.getDayOfMonth());

        employeeRepository
                .findActiveByBirthdayNextWeek(now)
                .forEach((e) -> {
                    if (LocalDate
                            .of(2020,
                                    e.getBirthday().getMonth(),
                                    e.getBirthday().getDayOfMonth()
                            )
                            .equals(date)) {
                        map.get("today").add(EmployeeBasicDto.of(e));
                    } else {
                        map.get("nextWeek").add(EmployeeBasicDto.of(e));
                    }
                });

        return map;
    }

    @Override
    public List<EmployeeBasicDto> search(String firstName, String lastName, String positionName) {
        EmployeePosition employeePosition = null;
        List<Employee> employeeList;

        firstName = firstName != null ? firstName : "";
        lastName = lastName != null ? lastName : "";

        if (positionName != null) {

            if (!(positionName.equalsIgnoreCase("null") || positionName.equalsIgnoreCase("none"))) {
                employeePosition = employeePositionRepository.findByPositionName(positionName).orElse(null);
                if (employeePosition == null) return Collections.emptyList();
            }

            employeeList = employeeRepository.findByFirstNameLikeAndLastNameLikeAndEmployeePositionIsAndInactiveIs(
                    "%" + firstName + "%",
                    "%" + lastName + "%",
                    employeePosition,
                    false
            );
        } else {
            employeeList = employeeRepository.findByFirstNameLikeAndLastNameLikeAndInactiveIs(
                    "%" + firstName + "%",
                    "%" + lastName + "%",
                    false
            );
        }

        return EmployeeBasicDto.of(employeeList);
    }

}
