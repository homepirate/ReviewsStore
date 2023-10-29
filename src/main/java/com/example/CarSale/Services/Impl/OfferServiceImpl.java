package com.example.CarSale.Services.Impl;

import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Services.Dtos.*;
import com.example.CarSale.Services.ModelService;
import com.example.CarSale.Services.UserService;
import com.example.CarSale.Views.AllOffersWithBrandView;
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


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
    public List<AllOffersWithBrandView> getAllOffersInfo() {
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
    public AllOffersWithBrandView createOfferByUser(CreateOfferFromUser offerModel){
        UserDto userDto = userService.getByUserName(offerModel.getUserName());
        ModelDto modelDto = modelService.getModelByName(offerModel.getModelName());
        OfferDto offerDto = modelMapper.map(offerModel, OfferDto.class);
//        OfferDto offerDto = new OfferDto();
//        offerDto.setDescription(offerModel.getDescription());
//        offerDto.setEngine(Engine.valueOf(offerModel.getEngine().toUpperCase()));
//        offerDto.setMileage(offerModel.getMileage());
//        offerDto.setPrice(offerModel.getPrice());
//        offerDto.setImageUrl(offerModel.getImageUrl());
//        offerDto.setYear(offerModel.getYear());
//        offerDto.setTransmission(Transmission.valueOf(offerModel.getTransmission().toUpperCase()));

        offerDto.setSeller(userDto);
        offerDto.setModel(modelDto);
        OfferDto offer = this.createOffer(offerDto);
        return offerRepository.getALLInfoOneOffer(offer.getId());
    }
}
