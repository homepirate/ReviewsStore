package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.ModelDto;
import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Models.Enums.Category;
import com.example.CarSale.Models.Model;
import com.example.CarSale.Repositories.ModelRepositiry;
import com.example.CarSale.Services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl  implements ModelService {
    private ModelRepositiry modelRepositiry;
    private ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepositiry modelRepositiry, ModelMapper modelMapper) {
        this.modelRepositiry = modelRepositiry;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ModelDto> getAll() {
        return modelRepositiry.findAll()
                .stream().map((model) -> modelMapper.map(model, ModelDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ModelDto> getModelByCategory(String category) {
            return modelRepositiry.findByCategory(Category.valueOf(category.toUpperCase()))
                    .stream().map((model) -> modelMapper.map(model, ModelDto.class)).collect(Collectors.toList());

    }

    @Override
    public ModelDto createModel(ModelDto modelDto) {
        Model model_model = modelMapper.map(modelDto, Model.class);
        return modelMapper.map(modelRepositiry.save(model_model), ModelDto.class);
    }

    @Override
    public void deleteModel(UUID modelId) {
        modelRepositiry.deleteById(modelId);
    }

    @Override
    public ModelDto changeImgUrl(UUID id, String newUrl) {
        Optional<Model> modelOptional = modelRepositiry.findById(id);
        if (modelOptional.isPresent()){
            Model model = modelOptional.get();
            model.setImageUrl(newUrl);
            return modelMapper.map(modelRepositiry.save(model), ModelDto.class);
        }
        else {
            return null;
        }
    }
}
