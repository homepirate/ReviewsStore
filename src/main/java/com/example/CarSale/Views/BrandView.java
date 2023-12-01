package com.example.CarSale.Views;

import java.util.List;

public class BrandView {
    private String brandName;
    private List<String> modelsName;


    public BrandView(String brandName, List<String> modelsName) {
        this.brandName = brandName;
        this.modelsName = modelsName;
    }

    public BrandView() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<String> getModelsName() {
        return modelsName;
    }

    public void setModelsName(List<String> modelsName) {
        this.modelsName = modelsName;
    }
}
