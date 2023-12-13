package com.example.CarSale.Views;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateOfferFromUser {
    private String userName;
    private String description;
    private String engine;
    private int mileage;
    private int price;

    private MultipartFile imageUrl;

    private String path;

    private int year;

    private String modelName;

    private String brandName;
    private String transmission;

    public CreateOfferFromUser(String userName, String description, String engine, int mileage, int price, MultipartFile imageUrl, int year, String modelName, String brandName, String transmission) {
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

    public String getPath() {
        return path;
    }

    public void setPath() {
        String fileName = imageUrl.getOriginalFilename();
        try {
            String filePath =  "/OffersImg/" + fileName;
            String projectPath = System.getProperty("user.dir");
            String fileSave = projectPath + "/OffersImg/" + fileName;
            imageUrl.transferTo(new File(fileSave));
            this.path = filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @NotEmpty(message = "Engine must not be null or empty!")

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @NotNull(message = "Please set mileage")
    @Min(value = 0, message = "Must be > 0")
    @Max(value = 1000000, message = "Must be < 1000000")
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @NotNull(message = "Please set price")
    @Min(value = 0, message = "Must be > 0")
    @Max(value = 1000000, message = "Must be < 1000000")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    @NotNull(message = "Set img")
    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        setPath();
    }


    //    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    @NotNull(message = "Please set year")
    @Min(value = 1900)
    @Max(value = 2023)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NotEmpty(message = "Model must not be null or empty!")

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @NotEmpty(message = "Brand must not be null or empty!")

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @NotEmpty(message = "Transmission must not be null or empty!")

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
