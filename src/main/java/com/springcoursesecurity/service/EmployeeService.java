package com.springcoursesecurity.service;

import com.springcoursesecurity.entity.Employee;
import com.springcoursesecurity.exception.EmployeeNotFoundException;
import com.springcoursesecurity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeDAO) {
        this.employeeRepository = employeeDAO;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Did not find employee id - " + id));
    }

    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

}
