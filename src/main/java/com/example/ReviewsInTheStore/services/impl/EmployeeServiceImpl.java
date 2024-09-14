package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private ModelMapper modelMapper;


    @Autowired
    public EmployeeServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
