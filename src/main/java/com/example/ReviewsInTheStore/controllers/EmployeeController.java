package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.EmployeeService;
import com.example.ReviewsInTheStore.services.dtos.AssignmentView;
import com.example.ReviewsInTheStore.services.dtos.EmployeeView;
import com.example.ReviewsInTheStore.services.dtos.EmployeeViewResource;
import com.example.contract_first.controllers.interfacies.EmployeeAPI;
import com.example.contract_first.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class EmployeeController implements EmployeeAPI {

    private EmployeeService employeeService;
    private ModelMapper modelMapper;


    @Autowired
    public void setEmployeeService(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<CollectionModel<EntityModel<EmployeeResponse>>> getAllEmployees() {
        List<EmployeeView> employeesView = employeeService.findAll();
        List<EmployeeResponse> employeeResponses = employeesView.stream()
                .map(this::mapViewToResponse)
                .toList();
        List<EntityModel<EmployeeResponse>> employees = employeeResponses.stream()
                .map(this::createEmployeeResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(employees,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withSelfRel()));
    }

    public ResponseEntity<EntityModel<EmployeeResponse>> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeView employeeView = modelMapper.map(employeeRequest, EmployeeView.class);
        EmployeeView createdEmployee = employeeService.createEmployee(employeeView);
        EmployeeResponse employeeResponse = mapViewToResponse(createdEmployee);

        EntityModel<EmployeeResponse> resource = createEmployeeResource(employeeResponse);
        return ResponseEntity.created(resource.getRequiredLink("self").toUri()).body(resource);
    }

    public ResponseEntity<EntityModel<EmployeeResponse>> getEmployeeById(@PathVariable UUID id) {
        EmployeeView employeeView = employeeService.findById(id);
        EmployeeResponse employeeResponse = mapViewToResponse(employeeView);
        EntityModel<EmployeeResponse> resource = createEmployeeResource(employeeResponse);
        return ResponseEntity.ok(resource);
    }

    public ResponseEntity<EntityModel<EmployeeResponse>> changeRole(@PathVariable UUID id, @RequestParam String role) {
        EmployeeView updatedEmployee = employeeService.changeRole(id, role);
        EmployeeResponse employeeResponse = mapViewToResponse(updatedEmployee);
        EntityModel<EmployeeResponse> resource = createEmployeeResource(employeeResponse);
        return ResponseEntity.ok(resource);
    }

    public ResponseEntity<String> deleteEmployee(@PathVariable UUID id) {
        String result = employeeService.deleteEmployee(id);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    private EmployeeViewResource createEmployeeResource(EmployeeResponse employeeResponse) {
        Map<String, Object> actions = Map.of(
                "changeRole", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).changeRole(employeeResponse.id(), "")).toUri().toString(),
                        "method", "PUT",
                        "accept", "application/json"
                ),
                "delete", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployee(employeeResponse.id())).toUri().toString(),
                        "method", "DELETE"
                )
        );

        return (EmployeeViewResource) new EmployeeViewResource(employeeResponse, actions)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employeeResponse.id())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }

    private EmployeeResponse mapViewToResponse(EmployeeView employeeView){
        List<AssignmentResponse> assignmentResponses = new ArrayList<>();
        if (employeeView.getAssignments() != null) {
            for (AssignmentView assignmentView : employeeView.getAssignments()) {
                assignmentResponses.add(new AssignmentResponse(
                        assignmentView.getId(),
                        assignmentView.getFeedbackId(),
                        assignmentView.getEmployerId()
                ));
            }
        }
        EmployeeResponse employeeResponse = new EmployeeResponse(
                employeeView.getId(),
                employeeView.getName(),
                mapRole(employeeView.getRole()),
                assignmentResponses
        );
        return employeeResponse;

    }

    Role mapRole(com.example.ReviewsInTheStore.models.Role role){
        switch (role) {
            case MANAGER -> {
                return Role.MANAGER;
            }
            case CASHIER -> {
                return Role.CASHIER;
            }
            case REVIEWER -> {
                return Role.REVIEWER;
            }
            default -> {
                return null;
            }
        }
    }
}
