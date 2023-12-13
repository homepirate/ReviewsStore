package com.example.CarSale.Services.Impl;

import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Services.Dtos.*;
import com.example.CarSale.Services.ModelService;
import com.example.CarSale.Services.UserService;
import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.Views.CreateOfferFromUser;
import com.example.CarSale.constants.Enums.Engine;
import com.example.CarSale.constants.Enums.Transmission;
import com.example.CarSale.Models.Offer;
import com.example.CarSale.Repositories.OfferRepository;
import com.example.CarSale.Services.OfferService;
import com.example.CarSale.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private BrandService brandService;
    private UserService userService;
    private ModelService modelService;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;


    @Autowired
    public OfferServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.validationUtil = validationUtil;

        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setOfferRepository(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        if (!this.validationUtil.isValid(offerDto)) {
            this.validationUtil
                    .violations(offerDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        } else {
            Offer offer_model = modelMapper.map(offerDto, Offer.class);
            return modelMapper.map(offerRepository.save(offer_model), OfferDto.class);
        }
        return null;
    }

    @Override
    public List<OfferDto> getAll() {


        return offerRepository.findAll()
                .stream().map((offer) -> modelMapper.map(offer, OfferDto.class)).collect(Collectors.toList());

    }

    @CacheEvict(cacheNames = {"offers", "UserOffers", "top3models"}, allEntries = true)
    @Override
    public void deleteOffer(UUID offerId) {
        this.offerRepository.deleteById(offerId);
    }

    @Override
    public List<OfferDto> getOfferByEngine(String engine) {
        return offerRepository.findByEngine(Engine.valueOf(engine.toUpperCase()))
                .stream().map((offer) -> modelMapper.map(offer, OfferDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<OfferDto> getOfferByTransmission(String transmission) {
        return offerRepository.findByTransmission(Transmission.valueOf(transmission.toUpperCase()))
                .stream().map((offer) -> modelMapper.map(offer, OfferDto.class)).collect(Collectors.toList());
    }

    @Cacheable(value = "offers", key = "#root.methodName")
    @Override
    public List<AllOfferWithBrandView> getAllOffersInfo() {
        List<Offer> offers = offerRepository.getAllOffersWithInfo();
        List<AllOfferWithBrandView> result = new ArrayList<>();
        for (Offer o : offers){
            AllOfferWithBrandView offer = modelMapper.map(o, AllOfferWithBrandView.class);
            offer.setBrandName(o.getModel().getBrand().getName());
            offer.setFirstName(o.getSeller().getFirstName());
            offer.setLastName(o.getSeller().getLastName());
            offer.setUsername(o.getSeller().getUsername());
            offer.setModelName(o.getModel().getName());
            offer.setImageUrl(o.getImageUrl());
            result.add(offer);
        }
        return result;
    }

    @Override
    public OfferDto changePrice(UUID offerId, int newPrice) {
        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isPresent()){
            Offer offer = offerOptional.get();
            offer.setPrice(newPrice);
            return modelMapper.map(offerRepository.save(offer), OfferDto.class);
        }
        else {
            return null;
        }
    }

    @Override
    public Map<String, LocalDateTime> getCreatedandModifiedInfo(UUID offerId) {
        return offerRepository.findOfferDetailsById(offerId);
    }

    @Override
    public OfferDto changeImgUrl(UUID id, String newUrl) {
        Optional<Offer> offerOptional = offerRepository.findById(id);
        if (offerOptional.isPresent()){
            Offer offer = offerOptional.get();
            offer.setImageUrl(newUrl);
            return modelMapper.map(offerRepository.save(offer), OfferDto.class);
        }
        else {
            return null;
        }
    }

    @CacheEvict(cacheNames = {"offers","UserOffers", "top3models"}, allEntries = true)
    @Override
    public AllOfferWithBrandView createOfferByUser(CreateOfferFromUser offerModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.getByUserName(authentication.getName());
//        UserDto userDto = userService.getByUserName("evgeniy.loginov");
        ModelDto modelDto = modelService.getModelByName(offerModel.getModelName());
        OfferDto offerDto = modelMapper.map(offerModel, OfferDto.class);
        offerDto.setImageUrl(offerModel.getPath());
        offerDto.setSeller(userDto);
        offerDto.setModel(modelDto);
        OfferDto offer = this.createOffer(offerDto);
        return modelMapper.map(offerRepository.getALLInfoOneOffer(offer.getId()), AllOfferWithBrandView.class);
    }

    @Override
    public AllOfferWithBrandView getOfferById(UUID offerId) {
        Offer offer = offerRepository.getALLInfoOneOffer(offerId);
        AllOfferWithBrandView result = modelMapper.map(offer, AllOfferWithBrandView.class);
        result.setBrandName(offer.getModel().getBrand().getName());
        result.setFirstName(offer.getSeller().getFirstName());
        result.setLastName(offer.getSeller().getLastName());
        result.setUsername(offer.getSeller().getUsername());
        result.setModelName(offer.getModel().getName());
        result.setImageUrl(offer.getImageUrl());
        return result;
    }

    public List<AllOfferWithBrandView> getFilteredOffers(Optional<List<String>> engines, Optional<List<String>> transmissions, String model){
        List<Engine> enginesFilters = new ArrayList<>();
        if (engines.isPresent()){
        for (String engine : engines.get()) {
            enginesFilters.add(Engine.valueOf(engine));
        }}
        else {
            enginesFilters = List.of(Engine.values());
        }
        List<Transmission> transmissionFilters = new ArrayList<>();
        if (transmissions.isPresent()){
        for (String transmission : transmissions.get()) {
            transmissionFilters.add(Transmission.valueOf(transmission));
        }}
        else {
            transmissionFilters = List.of(Transmission.values());
        }

        List<Offer> offers = offerRepository.getFilteredOffers(enginesFilters, transmissionFilters, model);
        List<AllOfferWithBrandView> result = new ArrayList<>();
        for (Offer o : offers){
            AllOfferWithBrandView offer = modelMapper.map(o, AllOfferWithBrandView.class);
            offer.setBrandName(o.getModel().getBrand().getName());
            offer.setFirstName(o.getSeller().getFirstName());
            offer.setLastName(o.getSeller().getLastName());
            offer.setUsername(o.getSeller().getUsername());
            offer.setModelName(o.getModel().getName());
            offer.setImageUrl(o.getImageUrl());
            result.add(offer);
        }
        return result;
    }

}
