package com.example.CarSale.web.controllers;

import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Views.ModelBrandView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private BrandService brandService;

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/{brand}/models")
    public @ResponseBody String getAllModelToUser(@PathVariable String brand, Model model){
        List<ModelBrandView> models = brandService.getBrandModelsToUser(brand);
        models.forEach(System.out::println);
        model.addAttribute("Allmodels", models);
        return "all-brand-models";
    }

    @GetMapping("/models")
    public ResponseEntity<List<ModelBrandView>> getAllModels(@RequestParam String brand){
       List<ModelBrandView> models = brandService.getBrandModelsToUser(brand);
       return ResponseEntity.status(HttpStatus.OK).body(models);
    }
}

