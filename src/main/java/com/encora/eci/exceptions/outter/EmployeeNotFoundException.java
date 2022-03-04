package com.encora.eci.exceptions.outter;

import com.encora.eci.util.ResponseMessages;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super(ResponseMessages.EMPLOYEE_NOT_FOUND_MESSAGE.value());
    }
}
