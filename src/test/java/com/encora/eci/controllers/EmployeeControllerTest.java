package com.encora.eci.controllers;

import com.encora.eci.dtos.EmployeeBasicDto;
import com.encora.eci.entities.Employee;
import com.encora.eci.exceptions.outter.EmployeeNotFoundException;
import com.encora.eci.services.EmployeeService;
import com.encora.eci.util.Util;
import com.encora.eci.util.ResponseMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    private static long EMPLOYEE_ID = 101;
    private static final String EMPLOYEE_PATH = "employees";
    private static final String HOST = "localhost";
    private static final String PROTOCOL = "http";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private List<EmployeeBasicDto> employees = Collections.emptyList();

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
        employees = EmployeeBasicDto.of(Util.createNEmployees(5));
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
    @DisplayName("Search Employees body has service employee list")
    void getEmployees() throws JsonProcessingException {
        String firstName = "ger";
        String lastName = "tor";
        String position = "pos";

        when(employeeService.search(firstName, lastName, position)).thenReturn(employees);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                getUrl(EMPLOYEE_PATH, "search?firstName=" + firstName + "&lastName=" + lastName + "&positionName=" + position),
                String.class
        );

        assertThat(responseEntity.getBody()).isEqualTo(MAPPER.writeValueAsString(employees));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(employeeService, times(1)).search(firstName, lastName, position);
    }

    @Test
    @DisplayName("Get Employee by id has service employee in body")
    void getEmployeesById() throws Exception {
        EmployeeBasicDto employee = employees.get(0);
        when(employeeService.findById(EMPLOYEE_ID)).thenReturn(employee);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(EMPLOYEE_PATH, String.valueOf(EMPLOYEE_ID)), String.class);

        assertThat(responseEntity.getBody()).isEqualTo(MAPPER.writeValueAsString(employee));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(employeeService, times(1)).findById(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("Get Employee by id has 404 code")
    void getEmployeeByIdNotFound() {
        when(employeeService.findById(EMPLOYEE_ID)).thenThrow(new EmployeeNotFoundException());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(EMPLOYEE_PATH, String.valueOf(EMPLOYEE_ID)), String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(ResponseMessages.EMPLOYEE_NOT_FOUND_MESSAGE.value());

        verify(employeeService, times(1)).findById(EMPLOYEE_ID);
    }

    @Test
    @DisplayName("Get Birthday info OK")
    void getBirthdayInfo() throws JsonProcessingException {
        var birthdayInfo = Map.of(
                "today", employees.subList(0,1),
                "nextWeek", employees.subList(2,4)
        );

        when(employeeService.getBirthdayInfo()).thenReturn(birthdayInfo);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(EMPLOYEE_PATH, "birthday-info"), String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(MAPPER.writeValueAsString(birthdayInfo));

        verify(employeeService, times(1)).getBirthdayInfo();

    }
}