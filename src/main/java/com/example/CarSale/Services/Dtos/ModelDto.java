package com.example.CarSale.Services.Dtos;

import com.example.CarSale.constants.Enums.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public class ModelDto {
    private UUID id;
    private String name;
    private Category category;
    private int startYear;
    private int endYear;
    private String imageUrl;
    private BrandDto brand;

    public ModelDto(UUID id, String name, Category category, int startYear, int endYear, String imageUrl, BrandDto brand) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.startYear = startYear;
        this.endYear = endYear;
        this.imageUrl = imageUrl;
        this.brand = brand;
    }

    public ModelDto() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Min(1886)
    @Max(2023)
    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }


    @Min(1886)
    @Max(2023)
    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "ModelDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", imageUrl='" + imageUrl + '\'' +
                ", brand=" + brand +
                '}';
    }
}
