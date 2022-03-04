package com.encora.eci.services;

import com.encora.eci.dtos.EmployeeBasicDto;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface EmployeeService {

    EmployeeBasicDto findById(long employeeNumber);

    Map<String, List<EmployeeBasicDto>> getBirthdayInfo();

    List<EmployeeBasicDto> search(String firstName, String lastName, String positionName);

}
