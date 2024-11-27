package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.AssignmentService;
import com.example.ReviewsInTheStore.services.dtos.AssignmentView;
import com.example.contract_first.controllers.interfacies.AssignmentAPI;
import com.example.contract_first.dto.AssignmentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AssignmentController implements AssignmentAPI {

    private AssignmentService assignmentService;
    private ModelMapper modelMapper;


    @Autowired
    public void setAssignmentService(AssignmentService assignmentService, ModelMapper modelMapper) {
        this.assignmentService = assignmentService;
        this.modelMapper = modelMapper;
    }

    public CollectionModel<EntityModel<AssignmentResponse>> getAllAssignments() {
        List<AssignmentResponse> assignmentResponses = assignmentService.findAll().stream()
                .map(assignment -> new AssignmentResponse(
                        assignment.getId(),
                        assignment.getFeedbackId(),
                        assignment.getEmployerId())
                )
                .toList();

        List<EntityModel<AssignmentResponse>> assignments = assignmentResponses.stream()
                .map(assignment -> EntityModel.of(assignment,
                        linkTo(methodOn(AssignmentController.class).getAssignmentById(assignment.id())).withRel("self"),
                        linkTo(methodOn(AssignmentController.class).getAllAssignments()).withRel("assignments"))
                )
                .collect(Collectors.toList());

        return CollectionModel.of(assignments,
                linkTo(methodOn(AssignmentController.class).getAllAssignments()).withSelfRel());
    }


    public EntityModel<AssignmentResponse> getAssignmentById(@PathVariable UUID id) {
        AssignmentView assignment = assignmentService.findById(id);
        AssignmentResponse assignmentResponse = new AssignmentResponse(assignment.getId(), assignment.getFeedbackId(), assignment.getEmployerId());
        return EntityModel.of(assignmentResponse,
                linkTo(methodOn(AssignmentController.class).getAssignmentById(id)).withRel("self"),
                linkTo(methodOn(AssignmentController.class).getAllAssignments()).withRel("assignments"));
    }

}
