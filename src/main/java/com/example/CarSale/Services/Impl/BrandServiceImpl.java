package com.example.CarSale.Services.Impl;

import com.example.CarSale.Models.Model;
import com.example.CarSale.Services.Dtos.BrandDto;
import com.example.CarSale.Services.Dtos.ModelDto;
import com.example.CarSale.Models.Brand;
import com.example.CarSale.Repositories.BrandRepository;
import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Views.BrandNameModelCountView;
import com.example.CarSale.Views.ModelBrandView;
import com.example.CarSale.Views.BrandView;
import com.example.CarSale.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
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
    @Cacheable(value = "brandModelsCache", key = "#brandName")
    public List<ModelBrandView> getBrandModelsToUser(String brandName) {
        return this.getBrandModels(brandName).stream()
                .map((model) -> modelMapper.map(model, ModelBrandView.class)).collect(Collectors.toList());
    }

    @Override
    public List<BrandView> getAll() {
        return brandRepository.findAll().stream()
                .map(brand -> new BrandView(brand.getName(), brand.getModels().stream()
                        .map(Model::getName)
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }


    @Cacheable("brands-model-count")
    @Override
    public List<BrandNameModelCountView> getBrandAndModelCount() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandNameModelCountView> brandModelCount = new ArrayList<>();
        for (Brand brand : brands) {
            BrandNameModelCountView brandCount = new BrandNameModelCountView();
            brandCount.setName(brand.getName());
            brandCount.setModelCount(brand.getModels().size());
            brandModelCount.add(brandCount);
        }
        return brandModelCount;
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
