package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.EmployeeService;
import com.example.ReviewsInTheStore.services.dtos.EmployeeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeView> getAll(){
        return employeeService.findAll();
    }

    @PostMapping
    public EmployeeView createEmployee(@RequestBody EmployeeView employeeView){
        return employeeService.createEmployee(employeeView);
    }
}
