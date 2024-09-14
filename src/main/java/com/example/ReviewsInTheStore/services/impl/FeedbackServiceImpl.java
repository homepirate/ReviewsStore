package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.services.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private ModelMapper modelMapper;

    @Autowired
    public FeedbackServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
