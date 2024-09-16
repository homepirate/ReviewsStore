package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.Assignment;
import com.example.ReviewsInTheStore.repositories.AssignmentRepository;
import com.example.ReviewsInTheStore.services.AssignmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private ModelMapper modelMapper;
    private AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, ModelMapper modelMapper) {
        this.assignmentRepository = assignmentRepository;
        this.modelMapper = modelMapper;
    }

//    @Override
//    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
//        Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);
//        Assignment savedAssignment = assignmentRepository.save(assignment);
//        return modelMapper.map(savedAssignment, AssignmentDTO.class);
//    }

    public void deleteAssignment(UUID assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }

//    @Override
//    public List<AssignmentDTO> find() {
//        return assignmentRepository.findAll().stream()
//                .map(assignment -> modelMapper.map(assignment, AssignmentDTO.class))
//                .collect(Collectors.toList());
//    }

}
