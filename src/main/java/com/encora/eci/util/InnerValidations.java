package com.encora.eci.util;

import com.encora.eci.entities.Employee;
import com.encora.eci.exceptions.inner.EmployeeRequiredException;
import com.encora.eci.exceptions.inner.PositionNameRequiredExeption;

import java.util.Objects;

public class InnerValidations {
    static public void employeeRequired(Employee employee) throws EmployeeRequiredException {
        if (Objects.isNull(employee)) throw new EmployeeRequiredException("Employee must not be null");
    }

    static public void positionNameRequired(String positionName) throws PositionNameRequiredExeption {
        if (isEmptyOrNullString(positionName)) throw new PositionNameRequiredExeption("Position name must not be null or empty");
    }

    static private boolean isEmptyOrNullString(String str){
        return Objects.isNull(str) || str.isEmpty();
    }
}
