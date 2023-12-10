package com.example.CarSale.Views;

import com.example.CarSale.constants.Enums.Category;

public class ModelView {

    private String brandName;
    private String name;
    private Category category;
    private int startYear;
    private int endYear;
    private String imageUrl;

    private int offersCount;

    public ModelView() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getOffersCount() {
        return offersCount;
    }

    public void setOffersCount(int offersCount) {
        this.offersCount = offersCount;
    }

    @Override
    public String toString() {
        return "ModelView{" +
                "brandName='" + brandName + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", imageUrl='" + imageUrl + '\'' +
                ", offersCount=" + offersCount +
                '}';
    }
}
