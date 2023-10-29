package com.example.CarSale.utils;

import java.time.LocalDate;

public class InfoForValid {


    public int getCurrentYear() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();

        int yearInt = Integer.parseInt(String.valueOf(year));
        return yearInt;
    }
}
