package com.example.CarSale.web.controllers;


import com.example.CarSale.Services.ModelService;
import com.example.CarSale.Views.BrandNameModelCountView;
import com.example.CarSale.Views.ModelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    private ModelService modelService;


    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("")
    public String getBrandModelCount(Model model){
        List<ModelView> modelViews = modelService.getTopModels();
        model.addAttribute("models", modelViews);
        modelViews.forEach(System.out::println);
        return "index";
    }
}
