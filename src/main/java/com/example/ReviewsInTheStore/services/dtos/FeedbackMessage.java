package com.example.ReviewsInTheStore.services.dtos;

import java.util.UUID;

public class FeedbackMessage extends FeedbackView{
    private String email;


    public FeedbackMessage(String message, UUID userId, String status, UUID id, UUID assignmentId, String email) {
        super(message, userId, status, id, assignmentId);
        this.email = email;
    }

    public FeedbackMessage() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
