package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.AssignmentService;
import com.example.ReviewsInTheStore.services.dtos.AssignmentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private AssignmentService assignmentService;

    @Autowired
    public void setAssignmentService(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public CollectionModel<EntityModel<AssignmentView>> getAllAssignments(){
        List<EntityModel<AssignmentView>> assignments = assignmentService.findAll().stream()
                .map(assignment -> EntityModel.of(assignment,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssignmentController.class).getAssignmentById(assignment.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssignmentController.class).getAllAssignments()).withRel("assignments")))
                .collect(Collectors.toList());

        return CollectionModel.of(assignments,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssignmentController.class).getAllAssignments()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<AssignmentView> getAssignmentById(@PathVariable UUID id) {
        AssignmentView assignmentView = assignmentService.findById(id);
        return EntityModel.of(assignmentView,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssignmentController.class).getAssignmentById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssignmentController.class).getAllAssignments()).withRel("assignments"));
    }
}
