package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.ModelDto;
import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Repositories.BrandRepository;
import com.example.CarSale.Repositories.UserRepository;
import com.example.CarSale.Services.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<ModelDto> getBrandModels(String name) {
            return brandRepository.findModelsByBrandName(name)
                    .stream().map((model) -> modelMapper.map(model, ModelDto.class)).collect(Collectors.toList());
        }
}
