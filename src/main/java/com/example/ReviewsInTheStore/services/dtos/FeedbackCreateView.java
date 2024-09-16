package com.example.ReviewsInTheStore.services.dtos;

import com.example.ReviewsInTheStore.models.Status;

import java.util.UUID;

public class FeedbackCreateView {

    private String message;
    private UUID userId;
    private String status;

    public FeedbackCreateView(String message, UUID userId, String status) {
        this.message = message;
        this.userId = userId;
        this.status = status;
    }

    public FeedbackCreateView() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}