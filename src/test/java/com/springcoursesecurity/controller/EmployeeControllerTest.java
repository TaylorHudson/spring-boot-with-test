package com.springcoursesecurity.controller;

import com.springcoursesecurity.entity.Employee;
import com.springcoursesecurity.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;

    List<Employee> employees;

    @AfterEach
    void tearDown() {
        reset(employeeService);
    }

    @Test
    @WithMockUser(roles = {"EMPLOYEE","MANAGER","ADMIN"})
    void shouldReturnAllEmployee() throws Exception {
        var emp = Employee.builder()
                .id(1)
                .email("emp1@example.com")
                .firstName("emp")
                .lastName("1")
                .build();
        employees = Collections.singletonList(emp);

        given(employeeService.findAll()).willReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("emp1@example.com"));

        then(employeeService).should().findAll();
    }

    @Test
    @WithMockUser(roles = {"EMPLOYEE","MANAGER","ADMIN"})
    void shouldReturnSingleEmployee() throws Exception {
        var mock = Employee.builder()
                .id(10)
                .email("mock@example.com")
                .firstName("mock")
                .lastName("test")
                .build();
        given(employeeService.findById(anyInt())).willReturn(mock);

        mockMvc.perform(get("/api/v1/employees/" + mock.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mock.getId())))
                .andExpect(jsonPath("$.email", is("mock@example.com")));

        then(employeeService).should().findById(anyInt());
    }

    @Test
    @WithMockUser(roles = {"MANAGER","ADMIN"})
    void shouldSaveEmployee() throws Exception {
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
    @WithMockUser(roles = {"EMPLOYEE"})
    void shouldFailBecauseUserDoesNotHavePermission() throws Exception {
        var mock = Employee.builder()
                .id(3)
                .email("mock3@example.com")
                .firstName("mock3")
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
                .andExpect(status().isForbidden());

        then(employeeService).should(never()).save(any(Employee.class));
    }

    @Test
    void updateTest() {
    }

    @Test
    void deleteById() {
    }

}