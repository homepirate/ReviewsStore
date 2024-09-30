package com.example.ReviewsInTheStore.services.dtos;

import java.util.List;
import java.util.UUID;

public class UserView {

    private UUID id;
    private String name;
    private String email;
    private List<FeedbackView> feedbacks;

    public UserView(UUID id, String name, String email, List<FeedbackView> feedbacks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.feedbacks = feedbacks;
    }

    public UserView(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserView() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<FeedbackView> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackView> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
