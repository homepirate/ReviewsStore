package com.example.CarSale.constants.Enums;

public enum Transmission {
    MANUAL (1),
    AUTOMATIC (2);
    private final int value;

    Transmission(int value) {
        this.value = value;
    }
}
