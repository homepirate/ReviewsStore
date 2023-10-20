package com.example.CarSale.Dtos;

import com.example.CarSale.Models.Enums.Engine;
import com.example.CarSale.Models.Enums.Transmission;

public class AllOffersWithBrandDto {
    private String brandName;
    private String modelName;
    private int price;
    private String imageUrl;
    private Engine engine;
    private int mileage;
    private int year;
    private Transmission transmission;
    private String firstName;
    private String lastName;

    public AllOffersWithBrandDto(String brandName, String modelName, int price, String imageUrl, Engine engine, int mileage, int year, Transmission transmission, String firstName, String lastName) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.engine = engine;
        this.mileage = mileage;
        this.year = year;
        this.transmission = transmission;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AllOffersWithBrandDto() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "AllOffersWithBrandDto{" +
                "brandName='" + brandName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", engine=" + engine +
                ", mileage=" + mileage +
                ", year=" + year +
                ", transmission=" + transmission +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
