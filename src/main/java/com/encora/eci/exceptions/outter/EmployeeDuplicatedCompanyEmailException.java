package com.encora.eci.exceptions.outter;

import com.encora.eci.util.ResponseMessages;

public class EmployeeDuplicatedCompanyEmailException extends RuntimeException {
    public EmployeeDuplicatedCompanyEmailException() {
        super(ResponseMessages.EMPLOYEE_DUPLICATED_COMPANY_EMAIL.value());
    }
}
