package com.example.CarSale.constants.Enums;

public enum Role {
    USER (1),
    ADMIN(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }
}
