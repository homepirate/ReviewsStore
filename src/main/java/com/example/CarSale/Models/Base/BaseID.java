package com.example.CarSale.Models.Base;

import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass

public class BaseID{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
