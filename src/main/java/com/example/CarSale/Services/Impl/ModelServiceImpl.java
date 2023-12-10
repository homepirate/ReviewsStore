package com.example.CarSale.Services.Impl;

import com.example.CarSale.Services.Dtos.ModelDto;
import com.example.CarSale.Views.ModelView;
import com.example.CarSale.constants.Enums.Category;
import com.example.CarSale.Models.Model;
import com.example.CarSale.Repositories.ModelRepositiry;
import com.example.CarSale.Services.ModelService;
import com.example.CarSale.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl  implements ModelService {
    private ModelRepositiry modelRepositiry;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;


    @Autowired
    public ModelServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.validationUtil = validationUtil;

        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setModelRepositiry(ModelRepositiry modelRepositiry) {
        this.modelRepositiry = modelRepositiry;
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
        if (!this.validationUtil.isValid(modelDto)) {
            this.validationUtil
                    .violations(modelDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        else {
            Model model_model = modelMapper.map(modelDto, Model.class);
            return modelMapper.map(modelRepositiry.save(model_model), ModelDto.class);
        }
        return null;
    }

    @Override
    public void deleteModel(UUID modelId) {
        modelRepositiry.deleteById(modelId);
    }

    @Override
    public ModelDto getModelByName(String name) {
        Model model = modelRepositiry.findByName(name);
        return modelMapper.map(model, ModelDto.class);
    }

    @Override
    public List<ModelView> getModelsByCategoryToUser(String category) {
        List<ModelDto> modelDtos = getModelByCategory(category);
        List<ModelView> modelViews = modelDtos.stream().map(modelDto -> modelMapper.map(modelDto, ModelView.class)).collect(Collectors.toList());
        for (int i=0; i< modelViews.size(); i++){
            modelViews.get(i).setBrandName(modelDtos.get(i).getBrand().getName());
        }
        return modelViews;
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

    @Override
    public List<ModelView> getTopModels() {
        List<Model> models = modelRepositiry.findTopPopularModels();
        List<ModelView> modelViews = models.stream()
                .map(model -> {
                    ModelView modelView = modelMapper.map(model, ModelView.class);
                    modelView.setBrandName(model.getBrand().getName());
                    modelView.setOffersCount(model.getOffers().size());
                    return modelView;
                })
                .collect(Collectors.toList());
        return modelViews;
    }
}
