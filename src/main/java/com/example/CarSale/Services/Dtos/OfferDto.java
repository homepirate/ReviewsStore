package com.example.CarSale.Services.Dtos;

import com.example.CarSale.constants.Enums.Engine;
import com.example.CarSale.constants.Enums.Transmission;
import com.example.CarSale.utils.InfoForValid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.UUID;


public class OfferDto {
    private UUID id;
    private String description;
    private Engine engine;
    private int mileage;
    private int price;
    private String imageUrl;

    private Transmission transmission;
    private int year;
    private UserDto seller;

    private ModelDto model;

    public OfferDto(String description, Engine engine, int mileage, int price, String imageUrl, Transmission transmission, int year, UserDto seller, ModelDto model) {
        this.description = description;
        this.engine = engine;
        this.mileage = mileage;
        this.price = price;
        this.imageUrl = imageUrl;
        this.transmission = transmission;
        this.year = year;
        this.seller = seller;
        this.model = model;
    }

    public OfferDto(UUID id, String description, Engine engine, int mileage, int price, String imageUrl, Transmission transmission, int year, UserDto seller, ModelDto model) {
        this.id = id;
        this.description = description;
        this.engine = engine;
        this.mileage = mileage;
        this.price = price;
        this.imageUrl = imageUrl;
        this.transmission = transmission;
        this.year = year;
        this.seller = seller;
        this.model = model;
    }

    public OfferDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Min(value=0, message = "Mileage must be > 0")

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Min(value=0, message = "Price must be > 0")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Min(1800)
    @Max(2023)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public UserDto getSeller() {
        return seller;
    }

    public void setSeller(UserDto seller) {
        this.seller = seller;
    }

    public ModelDto getModel() {
        return model;
    }

    public void setModel(ModelDto model) {
        this.model = model;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OfferDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", engine=" + engine +
                ", mileage=" + mileage +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", transmission=" + transmission +
                ", year=" + year +
                ", seller=" + seller +
                ", model=" + model +
                '}';
    }
}
