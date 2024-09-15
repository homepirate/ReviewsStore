package com.example.ReviewsInTheStore.services;

import com.example.ReviewsInTheStore.services.dtos.AssignmentDTO;

import java.util.List;
import java.util.UUID;

public interface AssignmentService {

    AssignmentDTO createAssignment(AssignmentDTO assignmentDto);
    void deleteAssignment(UUID assignmentId);
    List<AssignmentDTO> find();
}
