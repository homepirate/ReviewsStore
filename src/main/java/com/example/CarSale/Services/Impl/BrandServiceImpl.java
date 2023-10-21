package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.BrandDto;
import com.example.CarSale.Dtos.ModelDto;
import com.example.CarSale.Models.Brand;
import com.example.CarSale.Repositories.BrandRepository;
import com.example.CarSale.Services.BrandService;
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

    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
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
