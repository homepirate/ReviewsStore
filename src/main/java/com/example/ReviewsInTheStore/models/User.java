package com.example.ReviewsInTheStore.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends Base{

    private String name;
    private String email;

    @Column(name="name", nullable = false)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}