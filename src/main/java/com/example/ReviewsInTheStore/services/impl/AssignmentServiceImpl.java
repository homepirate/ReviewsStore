package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.services.AssignmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private ModelMapper modelMapper;

    @Autowired
    public AssignmentServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
