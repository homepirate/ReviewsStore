package com.example.CarSale.web.controllers;

import com.example.CarSale.Services.Dtos.ModelDto;
import com.example.CarSale.Services.ModelService;
import com.example.CarSale.Views.ModelView;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/models")
public class ModelController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private ModelService modelService;

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/{category}")
    public String getModelsByCategory(@PathVariable String category, Model model){
        LOG.log(Level.INFO, "watch models-by-category page");
        List<ModelView> modelViews = modelService.getModelsByCategoryToUser(category);
        modelViews.forEach(System.out::println);
        model.addAttribute("models", modelViews);
        return "models-by-category";
    }
}
