package com.example.CarSale.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String projectPath = System.getProperty("user.dir");
        String fileSave = projectPath + "/OffersImg/";
        registry.addResourceHandler("/OffersImg/**")
                .addResourceLocations("file:" + fileSave);
    }
}

