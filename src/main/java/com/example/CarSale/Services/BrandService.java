package com.example.CarSale.Services;

import com.example.CarSale.Dtos.ModelDto;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<ModelDto> getBrandModels(String name);
}
