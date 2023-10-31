package com.example.CarSale.Views;

public class BrandNameModelCountView {

    private String name;
    private int modelCount;

    public BrandNameModelCountView() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModelCount() {
        return modelCount;
    }

    public void setModelCount(int modelCount) {
        this.modelCount = modelCount;
    }

    @Override
    public String toString() {
        return "BrandNameModelCountView{" +
                "name='" + name + '\'' +
                ", modelCount=" + modelCount +
                '}';
    }
}
