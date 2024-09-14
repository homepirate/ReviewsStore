package com.example.ReviewsInTheStore.models;
import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass

public class Base{

    protected UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
