package com.example.CarSale.Services;

import com.example.CarSale.Services.Dtos.BrandDto;
import com.example.CarSale.Services.Dtos.ModelDto;
import com.example.CarSale.Views.ModelBrandView;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<ModelDto> getBrandModels(String name);

    BrandDto createBrand(BrandDto brandDto);

    void deleteBrand(UUID branId);
    BrandDto getBrandByName(String name);

    List<ModelBrandView> getBrandModelsToUser(String brandName);
}
