package com.example.CarSale.Dtos;

import com.example.CarSale.Models.Model;

import java.util.List;
import java.util.UUID;

public class BrandDto {
    private UUID id;
    private String name;

    public BrandDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public BrandDto() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BrandDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
