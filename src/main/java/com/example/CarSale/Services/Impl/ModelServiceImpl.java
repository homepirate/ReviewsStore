package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.ModelDto;
import com.example.CarSale.Dtos.UserDto;
import com.example.CarSale.Repositories.ModelRepositiry;
import com.example.CarSale.Services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl  implements ModelService {
    @Autowired
    private ModelRepositiry modelRepositiry;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ModelDto> getAll() {
        return modelRepositiry.findAll()
                .stream().map((model) -> modelMapper.map(model, ModelDto.class)).collect(Collectors.toList());
    }
}
