package com.example.ReviewsInTheStore.services.dtos;

import com.example.ReviewsInTheStore.models.Status;

import java.util.UUID;

public class FeedbackDTO {
    private UUID id;
    private String message;
    private UserView submittedBy;
    private Status status;
    private AssignmentView assignment;


    public FeedbackDTO(UUID id, String message, UserView submittedBy, Status status, AssignmentView assignment) {
        this.id = id;
        this.message = message;
        this.submittedBy = submittedBy;
        this.status = status;
        this.assignment = assignment;
    }

    public FeedbackDTO() {
    }

    public UUID getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserView getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(UserView submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AssignmentView getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentView assignment) {
        this.assignment = assignment;
    }
}
