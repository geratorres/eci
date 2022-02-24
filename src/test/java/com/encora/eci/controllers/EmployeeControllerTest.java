package com.encora.eci.controllers;

import com.encora.eci.entities.Employee;
import com.encora.eci.services.EmployeeService;
import com.encora.eci.util.ResponseMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    private static long EMPLOYEE_NUMBER = 101;
    private static final String EMPLOYEE_PATH = "employees";
    private static final String HOST = "localhost";
    private static final String PROTOCOL = "http";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Employee employee;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @BeforeAll
    static void setup() {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @BeforeEach
    void beforeEach() {
        employee = new Employee();
        employee.setEmployeeNumber(EMPLOYEE_NUMBER);
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setAddress("1 washington st.");
        employee.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 1));
        employee.setCompanyEmail("jhon.smith@encora.com");
        employee.setPersonalEmail("jsmith@gmail.com");
        employee.setGender("MALE");
        employee.setPhoneNumber("111111111");

        employee2 = new Employee();
        employee2.setEmployeeNumber(++EMPLOYEE_NUMBER);
        employee2.setFirstName("John2");
        employee2.setLastName("Smith2");
        employee2.setAddress("2 washington st.");
        employee2.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 1));
        employee2.setCompanyEmail("jhon.smith2@encora.com");
        employee2.setPersonalEmail("jsmith2@gmail.com");
        employee2.setGender("MALE");
        employee2.setPhoneNumber("2222222");

        employee3 = new Employee();
        employee3.setEmployeeNumber(++EMPLOYEE_NUMBER);
        employee3.setFirstName("John3");
        employee3.setLastName("Smith3");
        employee3.setAddress("3 washington st.");
        employee3.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 2));
        employee3.setCompanyEmail("jhon.smith3@encora.com");
        employee3.setPersonalEmail("jsmith3@gmail.com");
        employee3.setGender("MALE");
        employee3.setPhoneNumber("33333333");

        employee4 = new Employee();
        employee4.setEmployeeNumber(++EMPLOYEE_NUMBER);
        employee4.setFirstName("John4");
        employee4.setLastName("Smith4");
        employee4.setAddress("123 washington st.");
        employee4.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 3));
        employee4.setCompanyEmail("jhon.smith4@encora.com");
        employee4.setPersonalEmail("jsmith4@gmail.com");
        employee4.setGender("MALE");
        employee4.setPhoneNumber("444444444");

        employee5 = new Employee();
        employee5.setEmployeeNumber(++EMPLOYEE_NUMBER);
        employee5.setFirstName("John5");
        employee5.setLastName("Smith5");
        employee5.setAddress("5 washington st.");
        employee5.setBirthday(LocalDate.of(1993, Calendar.FEBRUARY, 3));
        employee5.setCompanyEmail("jhon.smith5@encora.com");
        employee5.setPersonalEmail("jsmith5@gmail.com");
        employee5.setGender("MALE");
        employee5.setPhoneNumber("5555555");
    }

    private String getUrl(String... path) {
        String parsedPath = Arrays.stream(path).reduce("", (acc, str) -> acc + "/" + str);
        return PROTOCOL + "://" + HOST + ":" + port + parsedPath;
    }

    @Test
    void contextLoads() {
        assertThat(employeeController).isNotNull();
    }

    @Test
    void getEmployees() throws JsonProcessingException {
        List<Employee> employees = List.of(employee, employee2);

        String firstName = "ger";
        String lastName = "tor";
        String position = "pos";

        when(employeeService.findEmployee(firstName, lastName, position)).thenReturn(employees);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                getUrl(EMPLOYEE_PATH + "?firstName=" + firstName + "&lastName=" + lastName + "&position=" + position),
                String.class
        );

        assertThat(responseEntity.getBody()).isEqualTo(MAPPER.writeValueAsString(employees));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(employeeService, times(1)).findEmployee(firstName, lastName, position);
    }

    @Test
    void getEmployeesById() throws Exception {
        when(employeeService.findById(EMPLOYEE_NUMBER)).thenReturn(employee);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(EMPLOYEE_PATH, String.valueOf(EMPLOYEE_NUMBER)), String.class);

        assertThat(responseEntity.getBody()).isEqualTo(MAPPER.writeValueAsString(employee));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(employeeService, times(1)).findById(EMPLOYEE_NUMBER);
    }

    @Test
    void getEmployeeByIdNotFound() {
        when(employeeService.findById(EMPLOYEE_NUMBER)).thenReturn(null);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(EMPLOYEE_PATH, String.valueOf(EMPLOYEE_NUMBER)), String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        //assertThat(responseEntity.getBody()).contains(ResponseMessages.EMPLOYEE_NOT_FOUND_MESSAGE.value());

        verify(employeeService, times(1)).findById(EMPLOYEE_NUMBER);
    }

    @Test
    void getBirthdayInfo() throws JsonProcessingException {
        var birthdayInfo = Map.of(
                "today", List.of(employee, employee2),
                "nextWeek", List.of(employee3, employee4, employee5)
        );

        when(employeeService.getBirthdayInfo()).thenReturn(birthdayInfo);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(EMPLOYEE_PATH, "birthday"), String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(MAPPER.writeValueAsString(birthdayInfo));

        verify(employeeService, times(1)).getBirthdayInfo();

    }
}