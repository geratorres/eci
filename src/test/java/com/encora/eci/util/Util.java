package com.encora.eci.util;

import com.encora.eci.entities.Employee;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public final class Util {
    public static List<Employee> createNEmployees(int count) {
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
}
