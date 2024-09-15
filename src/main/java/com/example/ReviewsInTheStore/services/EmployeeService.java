package com.example.ReviewsInTheStore.services;

import com.example.ReviewsInTheStore.services.dtos.EmployeeDTO;
import com.example.ReviewsInTheStore.services.dtos.FeedbackDTO;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDto);
    void deleteEmployee(UUID employeeId);
    List<EmployeeDTO> find();
}
