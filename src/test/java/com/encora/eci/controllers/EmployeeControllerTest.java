package com.encora.eci.controllers;

import com.encora.eci.entities.Employee;
import com.encora.eci.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Calendar;

import static com.encora.eci.controllers.EmployeeController.EMPLOYEE_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    private static final long EMPLOYEE_NUMBER = 101;
    private static final String ENDPOINT = "employee";
    private static final String HOST = "localhost";
    private static final String PROTOCOL = "http";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void contextLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    private String getUrl() {
        return PROTOCOL + "://" + HOST + ":" + port + "/" + ENDPOINT + "/" + EMPLOYEE_NUMBER;
    }

    @Test
    void getEmployee() throws Exception {

        Employee employee = new Employee();
        employee.setEmployeeNumber(EMPLOYEE_NUMBER);
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setAddress("123 washington st.");
        employee.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 1));
        employee.setCompanyEmail("jhon.smith@encora.com");
        employee.setPersonalEmail("jsmith@gmail.com");
        employee.setGender("MALE");
        employee.setPhoneNumber("1234567890");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = mapper.writeValueAsString(employee);

        when(employeeService.findById(EMPLOYEE_NUMBER)).thenReturn(employee);

        assertThat(this.restTemplate.getForObject(getUrl(),
                String.class)).contains(json);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(), String.class);
        assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));    }

    @Test
    void getEmployeeNotFound() throws Exception {
        when(employeeService.findById(EMPLOYEE_NUMBER)).thenReturn(null);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(), String.class);

        assertThat(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND));
        assertThat(responseEntity.getBody().equals(EMPLOYEE_NOT_FOUND_MESSAGE));
    }
}