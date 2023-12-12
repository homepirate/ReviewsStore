package com.example.CarSale.web.controllers;


import com.example.CarSale.Services.ModelService;
import com.example.CarSale.Views.BrandNameModelCountView;
import com.example.CarSale.Views.ModelView;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    private ModelService modelService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);


    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("")
    public String getBrandModelCount(Model model, Principal principal){
        List<ModelView> modelViews = modelService.getTopModels();
        String username = (principal != null) ? principal.getName() : "null";
        LOG.log(Level.INFO, "Username: " + username  + " watch home page");
        model.addAttribute("models", modelViews);
        return "index";
    }
}
