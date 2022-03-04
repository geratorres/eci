package com.encora.eci.exceptions;

import com.encora.eci.exceptions.outter.EmployeeDuplicatedCompanyEmailException;
import com.encora.eci.exceptions.outter.EmployeeNotFoundException;
import com.encora.eci.exceptions.outter.EmployeePositionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EmployeeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EmployeeDuplicatedCompanyEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String employeeDuplicatedCopanyEmailHandler(EmployeeDuplicatedCompanyEmailException ex) {
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(EmployeePositionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String EmployeePositionNotFoundHandler(EmployeePositionNotFoundException ex) {
        return ex.getMessage();
    }
}