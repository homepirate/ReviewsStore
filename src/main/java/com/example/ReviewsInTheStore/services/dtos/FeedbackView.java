package com.example.ReviewsInTheStore.services.dtos;

import com.example.ReviewsInTheStore.models.Status;

import java.util.UUID;

public class FeedbackView extends FeedbackCreateView{
    private UUID id;

    public FeedbackView(String message, UserDTO submittedBy, String status, UUID id) {
        super(message, submittedBy, status);
        this.id = id;
    }

    public FeedbackView() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
