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

    @Override
    public List<AllOfferWithBrandView> getAllOffersInfo() {
        return offerRepository.getAllOffersWithInfo();
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

    @Override
    public AllOfferWithBrandView createOfferByUser(CreateOfferFromUser offerModel){
//        UserDto userDto = userService.getByUserName(offerModel.getUserName());
        UserDto userDto = userService.getByUserName("evgeniy.loginov");
        ModelDto modelDto = modelService.getModelByName(offerModel.getModelName());
        OfferDto offerDto = modelMapper.map(offerModel, OfferDto.class);
        offerDto.setImageUrl(offerModel.getPath());
        offerDto.setSeller(userDto);
        offerDto.setModel(modelDto);
        OfferDto offer = this.createOffer(offerDto);
        return offerRepository.getALLInfoOneOffer(offer.getId());
    }

    @Override
    public List<AllOfferWithBrandView> getOfferByTransmissionToUser(String transmission) {
        List<OfferDto> offerDtos = this.getOfferByTransmission(transmission);
        List<AllOfferWithBrandView> allOfferWithBrandViews =  offerDtos.stream().map(offer -> modelMapper.map(offer, AllOfferWithBrandView.class)).collect(Collectors.toList());
        for (int i=0; i< allOfferWithBrandViews.size(); i++ ){
            allOfferWithBrandViews.get(i).setFirstName(offerDtos.get(i).getSeller().getFirstName());
            allOfferWithBrandViews.get(i).setLastName(offerDtos.get(i).getSeller().getLastName());
            allOfferWithBrandViews.get(i).setBrandName(offerDtos.get(i).getModel().getBrand().getName());
        }
        return allOfferWithBrandViews;
    }


    public List<AllOfferWithBrandView> getOfferByEngineToUser(String engine) {
        List<OfferDto> offerDtos = this.getOfferByEngine(engine);
        List<AllOfferWithBrandView> allOfferWithBrandViews =  offerDtos.stream().map(offer -> modelMapper.map(offer, AllOfferWithBrandView.class)).collect(Collectors.toList());
        for (int i=0; i< allOfferWithBrandViews.size(); i++ ){
            allOfferWithBrandViews.get(i).setFirstName(offerDtos.get(i).getSeller().getFirstName());
            allOfferWithBrandViews.get(i).setLastName(offerDtos.get(i).getSeller().getLastName());
            allOfferWithBrandViews.get(i).setBrandName(offerDtos.get(i).getModel().getBrand().getName());
        }
        return allOfferWithBrandViews;
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
        return offerRepository.getFilteredOffers(enginesFilters, transmissionFilters, model);
    }

}
