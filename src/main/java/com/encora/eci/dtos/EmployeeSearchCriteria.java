package com.encora.eci.dtos;

import lombok.Data;

@Deprecated
@Data
public class EmployeeSearchCriteria {

    private String firstName;
    private String lastName;
    private String positionName;
}
