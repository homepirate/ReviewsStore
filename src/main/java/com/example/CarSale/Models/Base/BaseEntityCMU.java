package com.example.CarSale.Models.Base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntityCMU extends BaseEntityCM{

    protected String imageUrl;

    @Column(name="image_url", nullable = false)

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
