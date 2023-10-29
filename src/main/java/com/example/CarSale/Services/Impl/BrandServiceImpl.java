package com.example.CarSale.Services.Impl;

import com.example.CarSale.Services.Dtos.BrandDto;
import com.example.CarSale.Services.Dtos.ModelDto;
import com.example.CarSale.Models.Brand;
import com.example.CarSale.Repositories.BrandRepository;
import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Views.ModelBrandView;
import com.example.CarSale.utils.ValidationUtil;
import jakarta.validation.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    @Autowired
    public BrandServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setBrandRepository(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandDto getBrandByName(String name){
        Brand brand = brandRepository.findByName(name);
        return modelMapper.map(brand, BrandDto.class);
    }

    @Override
    public List<ModelBrandView> getBrandModelsToUser(String brandName) {
        return this.getBrandModels(brandName).stream()
                .map((model) -> modelMapper.map(model, ModelBrandView.class)).collect(Collectors.toList());
    }

    @Override
    public List<ModelDto> getBrandModels(String name) {
            return brandRepository.findModelsByBrandName(name)
                    .stream().map((model) -> modelMapper.map(model, ModelDto.class)).collect(Collectors.toList());
        }

    @Override
    public BrandDto createBrand(BrandDto brandDto) {
        Brand brand_model = modelMapper.map(brandDto, Brand.class);
        return modelMapper.map(brandRepository.save(brand_model), BrandDto.class);
    }

    @Override
    public void deleteBrand(UUID branId) {
        brandRepository.deleteById(branId);
    }
}
