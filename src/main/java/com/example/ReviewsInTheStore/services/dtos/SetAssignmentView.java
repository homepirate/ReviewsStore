package com.example.ReviewsInTheStore.services.dtos;

import java.util.UUID;

public class SetAssignmentView {

    private UUID employeeId;
    private UUID feedbackId;

    public SetAssignmentView(UUID employeeId, UUID feedbackId) {
        this.employeeId = employeeId;
        this.feedbackId = feedbackId;
    }

    public SetAssignmentView() {
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(UUID feedbackId) {
        this.feedbackId = feedbackId;
    }
}
