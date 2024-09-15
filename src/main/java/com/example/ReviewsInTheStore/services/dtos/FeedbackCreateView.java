package com.example.ReviewsInTheStore.services.dtos;

import com.example.ReviewsInTheStore.models.Status;

public class FeedbackCreateView {

    private String message;
    private UserDTO submittedBy;
    private String status;

    public FeedbackCreateView(String message, UserDTO submittedBy, String status) {
        this.message = message;
        this.submittedBy = submittedBy;
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

    public UserDTO getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(UserDTO submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
