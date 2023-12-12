package com.example.CarSale.web.controllers;

import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Views.BrandNameModelCountView;
import com.example.CarSale.Views.ModelBrandView;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import org.springframework.util.StopWatch;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private BrandService brandService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/{brand}/models")
    public String getAllModelToUser(@PathVariable String brand, Model model){
        LOG.log(Level.INFO, "Watch all " + brand + " models");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<ModelBrandView> models = brandService.getBrandModelsToUser(brand);
        stopWatch.stop();
        System.out.println("Время выполнения getAllModelToUser: " + stopWatch.getTotalTimeMillis() + " мс");
        models.forEach(System.out::println);
        model.addAttribute("Allmodels", models);
        return "all-brand-models.html";
    }

    @GetMapping("")
    public String getBrandModelCount(Model model){
        LOG.log(Level.INFO, "Watch all brands");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<BrandNameModelCountView> brandsModelCount = brandService.getBrandAndModelCount();
        stopWatch.stop();
        System.out.println("Время выполнения getBrandAndModelCount: " + stopWatch.getTotalTimeMillis() + " мс");
        model.addAttribute("brands", brandsModelCount);
//        brandsModelCount.forEach(System.out::println);
        return "brands-page";
    }



    @GetMapping("/models")
    public ResponseEntity<List<ModelBrandView>> getAllModels(@RequestParam String brand){
        LOG.log(Level.INFO, "Get models for " + brand);
       List<ModelBrandView> models = brandService.getBrandModelsToUser(brand);
       return ResponseEntity.status(HttpStatus.OK).body(models);
    }
}

