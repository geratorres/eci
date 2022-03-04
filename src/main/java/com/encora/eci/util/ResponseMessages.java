package com.encora.eci.util;

public enum ResponseMessages {
    EMPLOYEE_NOT_FOUND_MESSAGE("Employee not found"),
    EMPLOYEE_DUPLICATED_COMPANY_EMAIL("Company email already exists."),
    EMPLOYEE_POSITION_NOT_FOUND_MESSAGE("Position not found")
    ;

    private final String value;

    ResponseMessages(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
}
