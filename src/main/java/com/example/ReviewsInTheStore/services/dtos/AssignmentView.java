package com.example.ReviewsInTheStore.services.dtos;

import com.example.ReviewsInTheStore.models.Employee;
import com.example.ReviewsInTheStore.models.Feedback;

import java.util.UUID;

public class AssignmentView {
    private UUID id;
    private UUID feedbackId;
    private UUID employerId;

    public AssignmentView(UUID id, UUID feedbackId, UUID employerId) {
        this.id = id;
        this.feedbackId = feedbackId;
        this.employerId = employerId;
    }

    public AssignmentView() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(UUID feedbackId) {
        this.feedbackId = feedbackId;
    }

    public UUID getEmployerId() {
        return employerId;
    }

    public void setEmployerId(UUID employerId) {
        this.employerId = employerId;
    }
}
