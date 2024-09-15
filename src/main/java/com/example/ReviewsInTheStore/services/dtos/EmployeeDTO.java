package com.example.ReviewsInTheStore.services.dtos;


import java.util.List;
import java.util.UUID;

public class EmployeeDTO {
    private UUID id;
    private String name;
    private String role;
    private List<AssignmentDTO> assignments;

    public EmployeeDTO(UUID id, String name, String role, List<AssignmentDTO> assignments) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.assignments = assignments;
    }

    public EmployeeDTO() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<AssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDTO> assignments) {
        this.assignments = assignments;
    }
}
