package com.example.ReviewsInTheStore.services.dtos;


import java.util.UUID;

public class FeedbackView extends FeedbackCreateView{
    private UUID id;
    private UUID assignmentId;

    public FeedbackView(String message, UUID userId, String status, UUID id, UUID assignmentId) {
        super(message, userId, status);
        this.id = id;
        this.assignmentId = assignmentId;
    }

    public FeedbackView() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(UUID assignmentId) {
        this.assignmentId = assignmentId;
    }
}
