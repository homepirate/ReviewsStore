package com.example.ReviewsInTheStore.services.dtos;

import java.util.UUID;

public class AssignmentDTO {
    private UUID id;

    private FeedbackDTO feedback;
    private EmployeeDTO assignedTo;

    public AssignmentDTO(UUID id, FeedbackDTO feedback, EmployeeDTO assignedTo) {
        this.id = id;
        this.feedback = feedback;
        this.assignedTo = assignedTo;
    }

    public AssignmentDTO() {
    }

    public UUID getId() {
        return id;
    }

    public FeedbackDTO getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackDTO feedback) {
        this.feedback = feedback;
    }

    public EmployeeDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(EmployeeDTO assignedTo) {
        this.assignedTo = assignedTo;
    }
}
