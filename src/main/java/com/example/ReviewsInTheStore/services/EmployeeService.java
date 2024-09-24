package com.example.ReviewsInTheStore.services;

import com.example.ReviewsInTheStore.models.Employee;
import com.example.ReviewsInTheStore.services.dtos.EmployeeView;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    EmployeeView createEmployee(EmployeeView employeeView);
    void deleteEmployee(UUID employeeId);
    List<EmployeeView> findAll();
    EmployeeView findById(UUID id);
}
