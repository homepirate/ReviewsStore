package com.example.ReviewsInTheStore.services.dtos;

import java.util.UUID;

public class UserView {

    private UUID id;
    private String name;
    private String email;

    public UserView(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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
}
