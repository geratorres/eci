package com.encora.eci.util;

public enum ResponseMessages {
    EMPLOYEE_NOT_FOUND_MESSAGE("Employee Not Found")
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
