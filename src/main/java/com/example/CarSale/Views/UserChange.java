package com.example.CarSale.Views;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UserChange {
    private String username;

    private String value;

    private MultipartFile imageUrl;

    public UserChange() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
        String fileName = imageUrl.getOriginalFilename();
        try {
            String filePath =  "/OffersImg/" + fileName;
            String projectPath = System.getProperty("user.dir");
            String fileSave = projectPath + "/OffersImg/" + fileName;
            imageUrl.transferTo(new File(fileSave));
            this.value = filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
