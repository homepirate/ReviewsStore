package com.example.CarSale.Services;

import com.example.CarSale.Services.Dtos.ModelDto;
import com.example.CarSale.Views.ModelView;

import java.util.List;
import java.util.UUID;

public interface ModelService extends GeneralSevice{

    List<ModelDto> getAll();

    List<ModelDto> getModelByCategory(String category);

    ModelDto createModel(ModelDto modelDto);

    void deleteModel(UUID modelId);
    ModelDto getModelByName(String name);

    List<ModelView> getModelsByCategoryToUser(String category);

}
