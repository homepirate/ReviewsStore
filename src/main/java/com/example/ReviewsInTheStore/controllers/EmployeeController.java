package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.EmployeeService;
import com.example.ReviewsInTheStore.services.dtos.EmployeeView;
import com.example.ReviewsInTheStore.services.dtos.EmployeeViewResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EmployeeView>>> getAllEmployees() {
        List<EntityModel<EmployeeView>> employees = employeeService.findAll().stream()
                .map(this::createEmployeeResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(employees,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<EmployeeView>> createEmployee(@RequestBody EmployeeView employeeView) {
        EmployeeView createdEmployee = employeeService.createEmployee(employeeView);
        EntityModel<EmployeeView> resource = createEmployeeResource(createdEmployee);

        return ResponseEntity.created(resource.getRequiredLink("self").toUri()).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeView>> getEmployeeById(@PathVariable UUID id) {
        EmployeeView employeeView = employeeService.findById(id);
        if (employeeView != null) {
            EntityModel<EmployeeView> resource = createEmployeeResource(employeeView);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeView>> changeRole(@PathVariable UUID id, @RequestParam String role) {
        EmployeeView updatedEmployee = employeeService.changeRole(id, role);
        if (updatedEmployee != null) {
            EntityModel<EmployeeView> resource = createEmployeeResource(updatedEmployee);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable UUID id) {
        String result = employeeService.deleteEmployee(id);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    private EmployeeViewResource createEmployeeResource(EmployeeView employeeView) {
        Map<String, Object> actions = Map.of(
                "changeRole", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).changeRole(employeeView.getId(), "")).toUri().toString(),
                        "method", "PUT",
                        "accept", "application/json"
                ),
                "delete", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployee(employeeView.getId())).toUri().toString(),
                        "method", "DELETE"
                )
        );

        return (EmployeeViewResource) new EmployeeViewResource(employeeView, actions)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getEmployeeById(employeeView.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }
}
