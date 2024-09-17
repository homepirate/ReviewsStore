package com.example.ReviewsInTheStore.services.dtos;

import java.util.UUID;

public class UpdateUserView {
    private UUID id;
    private String email;

    public UpdateUserView(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UpdateUserView() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
