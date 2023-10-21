package com.example.CarSale.Services;

import java.util.UUID;

public interface GeneralSevice<T> {
    T changeImgUrl(UUID id, String newUrl);
}
