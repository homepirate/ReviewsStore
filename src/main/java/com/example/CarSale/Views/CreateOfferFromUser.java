package com.example.CarSale.Views;

public class CreateOfferFromUser {
    private String userName;
    private String description;
    private String engine;
    private int mileage;
    private int price;

    private String imageUrl;

    private int year;

    private String modelName;

    private String brandName;
    private String transmission;

    public CreateOfferFromUser(String userName, String description, String engine, int mileage, int price, String imageUrl, int year, String modelName, String brandName, String transmission) {
        this.userName = userName;
        this.description = description;
        this.engine = engine;
        this.mileage = mileage;
        this.price = price;
        this.imageUrl = imageUrl;
        this.year = year;
        this.modelName = modelName;
        this.brandName = brandName;
        this.transmission = transmission;
    }

    public CreateOfferFromUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
