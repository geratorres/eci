package com.encora.eci.exceptions.outter;

import com.encora.eci.util.ResponseMessages;

public class EmployeePositionNotFoundException extends RuntimeException {
    public EmployeePositionNotFoundException() {
        super(ResponseMessages.EMPLOYEE_POSITION_NOT_FOUND_MESSAGE.value());
    }
}
