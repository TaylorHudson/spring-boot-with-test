package com.springcoursesecurity.controller;

import com.springcoursesecurity.entity.Employee;
import com.springcoursesecurity.exception.EmployeeNotFoundException;
import com.springcoursesecurity.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> entities = employeeService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(entities);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Integer id) {
        Employee entity = employeeService.findById(id);

        if (entity == null)
            throw new EmployeeNotFoundException("Employee id not found - " + id);

        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> save(@RequestBody Employee request) {
        request.setId(0);

        Employee entity = employeeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> update(@RequestBody Employee request) {
        Employee entity = employeeService.save(request);
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        Employee entity = employeeService.findById(id);

        if (entity == null)
            throw new EmployeeNotFoundException("Employee id not found - " + id);

        employeeService.deleteById(id);

        String message = "Deleted employee id - " + id;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

}
