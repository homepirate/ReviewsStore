package com.example.CarSale.web.controllers;

import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Views.ModelBrandView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private BrandService brandService;

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/models")
    public ResponseEntity<List<ModelBrandView>> getAllModels(@RequestParam String brand){
       List<ModelBrandView> models = brandService.getBrandModelsToUser(brand);
       return ResponseEntity.status(HttpStatus.OK).body(models);
    }
}

