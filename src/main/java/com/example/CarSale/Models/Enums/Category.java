package com.example.CarSale.Models.Enums;

public enum Category {
    CAR (1),
    BUSS (2),
    TRUCK (3),
    MOTORCYCLE (4);
    private final int value;

    Category(int value) {
        this.value = value;
    }
}
