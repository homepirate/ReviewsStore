package com.example.ReviewsInTheStore.services.dtos;

import com.example.ReviewsInTheStore.models.Role;

import java.util.List;
import java.util.UUID;

public class EmployeeView {

    private UUID id;
    private String name;
    private Role role;
    private List<AssignmentView> assignmentViewList;

    public EmployeeView(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public EmployeeView(UUID id, String name, Role role, List<AssignmentView> assignmentViewList) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.assignmentViewList = assignmentViewList;
    }

    public EmployeeView() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<AssignmentView> getAssignmentViewList() {
        return assignmentViewList;
    }

    public void setAssignmentViewList(List<AssignmentView> assignmentViewList) {
        this.assignmentViewList = assignmentViewList;
    }
}
