package com.example.ReviewsInTheStore.services;

import com.example.ReviewsInTheStore.services.dtos.AssignmentView;

import java.util.List;
import java.util.UUID;

public interface AssignmentService {

    void deleteAssignment(UUID assignmentId);
    List<AssignmentView> findAll();
    AssignmentView findById(UUID id);
}
