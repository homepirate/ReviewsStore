package com.example.ReviewsInTheStore.services.dtos;


import java.util.UUID;

public class FeedbackView extends FeedbackCreateView{
    private UUID id;

    public FeedbackView(String message, UUID userId, String status, UUID id) {
        super(message, userId, status);
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
