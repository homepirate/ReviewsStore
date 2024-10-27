package com.example.ReviewsInTheStore.services.dtos;


import java.util.UUID;

public class FeedbackView extends FeedbackCreateView{
    private UUID id;
    private UUID assignmentId;
    private String status;


    public FeedbackView(String message, UUID userId, String status, UUID id, UUID assignmentId) {
        super(userId, message);
        this.id = id;
        this.assignmentId = assignmentId;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
