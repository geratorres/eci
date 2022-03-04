package com.encora.eci.services;

import com.encora.eci.dtos.EmployeeHistoryDto;
import com.encora.eci.entities.Employee;
import com.encora.eci.entities.EmployeePosition;
import com.encora.eci.entities.EmployeePositionHistory;
import com.encora.eci.exceptions.outter.EmployeeDuplicatedCompanyEmailException;
import com.encora.eci.exceptions.outter.EmployeeNotFoundException;
import com.encora.eci.exceptions.outter.EmployeePositionNotFoundException;
import com.encora.eci.repositories.EmployeePositionHistoryRepository;
import com.encora.eci.repositories.EmployeePositionRepository;
import com.encora.eci.repositories.EmployeeRepository;
import com.encora.eci.util.InnerValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeAdminServiceImpl implements EmployeeAdminService {

    private EmployeeRepository employeeRepository;
    private EmployeePositionRepository employeePositionRepository;
    private EmployeePositionHistoryRepository employeePositionHistoryRepository;

    @Autowired
    public EmployeeAdminServiceImpl(EmployeeRepository employeeRepository, EmployeePositionRepository employeePositionRepository, EmployeePositionHistoryRepository employeePositionHistoryRepository) {
        this.employeeRepository = employeeRepository;
        this.employeePositionRepository = employeePositionRepository;
        this.employeePositionHistoryRepository = employeePositionHistoryRepository;
    }

    @Override
    public Employee create(Employee employee) {
        InnerValidations.employeeRequired(employee);

        if (employee.getCompanyEmail() == null || employee.getCompanyEmail().isEmpty()) {
            employee.setCompanyEmail(getCompanyEmailForEmployee(employee));
        }

        try {
            employee = employeeRepository.save(employee);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMostSpecificCause().toString().contains(employee.getCompanyEmail())) {
                throw new EmployeeDuplicatedCompanyEmailException();
            }

            throw ex;
        }
        return employee;
    }

    private String getCompanyEmailForEmployee(Employee employee) {
        InnerValidations.employeeRequired(employee);

        String companyEmailUserName = employee.getFirstName().replaceAll("\\s+", "") +
                "." +
                employee.getLastName().replaceAll("\\s+", "");

        long count = employeeRepository.getCompanyEmailCount(companyEmailUserName);

        if (count > 0) {
            companyEmailUserName += String.valueOf(count);
        }

        return companyEmailUserName + "@encora.com";
    }


    @Override
    public Employee inactivateEmployee(long employeeNumber) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeNumber);

        if (employeeOpt.isEmpty()) throw new EmployeeNotFoundException();

        employeeOpt.get().setInactive(true);

        return employeeRepository.save(employeeOpt.get());
    }

    @Override
    public Employee setPositionAndSalary(long employeeId, long employeePositionId, double salary) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (employeeOpt.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        Optional<EmployeePosition> employeePositionOpt = employeePositionRepository.findById(employeePositionId);

        if (employeePositionOpt.isEmpty()) {
            throw new EmployeePositionNotFoundException();
        }

        employeeOpt.get().setSalary(salary);
        employeeOpt.get().setEmployeePosition(employeePositionOpt.get());

        employeePositionHistoryRepository.save(new EmployeePositionHistory(employeeOpt.get(), employeePositionOpt.get(), salary));

        return create(employeeOpt.get());

    }

    @Override
    public Employee findById(long employeeNumber) {
        return employeeRepository.findById(employeeNumber).orElse(null);
    }

    @Override
    public List<Employee> search(String firstName, String lastName, String positionName) {
        EmployeePosition employeePosition = null;
        List<Employee> employeeList;

        firstName = firstName != null ? firstName : "";
        lastName = lastName != null ? lastName : "";

        if (positionName != null) {

            if (!(positionName.equalsIgnoreCase("null") || positionName.equalsIgnoreCase("none"))) {
                employeePosition = employeePositionRepository.findByPositionName(positionName).orElse(null);
                if (employeePosition == null) return Collections.emptyList();
            }

            employeeList = employeeRepository.findByFirstNameLikeAndLastNameLikeAndEmployeePositionIs(
                    "%" + firstName + "%",
                    "%" + lastName + "%",
                    employeePosition
            );
        } else {
            employeeList = employeeRepository.findByFirstNameLikeAndLastNameLike(
                    "%" + firstName + "%",
                    "%" + lastName + "%"
            );
        }

        return employeeList;
    }

    @Override
    public EmployeeHistoryDto getEmployeeHistory(long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);

        if (employeeOpt.isEmpty()) throw new EmployeeNotFoundException();

        List<EmployeePositionHistory> history = employeePositionHistoryRepository.findByEmployee(employeeOpt.get());

        return new EmployeeHistoryDto(employeeOpt.get(), history);
    }
}
