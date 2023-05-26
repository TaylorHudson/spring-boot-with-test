package com.springcoursesecurity.controller;

import com.springcoursesecurity.entity.Employee;
import com.springcoursesecurity.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    MockMvc mockMvc;

    List<Employee> employees;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void controllerFindAllTest() throws Exception {
        var emp = Employee.builder()
                .id(1)
                .email("emp1@example.com")
                .firstName("emp")
                .lastName("1")
                .build();
        employees = Collections.singletonList(emp);

        given(employeeService.findAll()).willReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("emp1@example.com"));

        then(employeeService).should().findAll();
    }

    @Test
    void findAllTest() {
        var emp = Employee.builder()
                .id(1)
                .email("emp1@example.com")
                .firstName("emp")
                .lastName("1")
                .build();
        employees = Collections.singletonList(emp);

        given(employeeService.findAll()).willReturn(employees);

        var response = employeeController.findAll();
        var employeeList = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(employeeList).isNotNull().hasSize(1);
        assertThat(employeeList.get(0).getId()).isEqualTo(1);
        assertThat(employeeList.get(0).getEmail()).isEqualTo("emp1@example.com");

        then(employeeService).should().findAll();
    }

    @Test
    void controllerFindByIdTest() throws Exception {
        var mock = Employee.builder()
                .id(10)
                .email("mock@example.com")
                .firstName("mock")
                .lastName("test")
                .build();
        given(employeeService.findById(anyInt())).willReturn(mock);

        mockMvc.perform(get("/api/v1/employees/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.email").value("mock@example.com"));

        then(employeeService).should().findById(anyInt());
    }

    @Test
    void controllerSaveTest() throws Exception {
        var mock = Employee.builder()
                .id(5)
                .email("mock2@example.com")
                .firstName("mock")
                .lastName("test")
                .build();
        given(employeeService.save(any(Employee.class))).willReturn(mock);

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"email\":\"mock2@example.com\"," +
                        " \"first_name\":\"mock\"," +
                        "\"last_name\":\"test\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.firstName").value("mock"));

        then(employeeService).should().save(any(Employee.class));
    }

    @Test
    void update() {
    }

    @Test
    void testUpdate() {
    }

}