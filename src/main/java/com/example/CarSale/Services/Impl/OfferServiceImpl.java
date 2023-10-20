package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.AllOffersWithBrandDto;
import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Models.Enums.Engine;
import com.example.CarSale.Models.Enums.Transmission;
import com.example.CarSale.Models.Offer;
import com.example.CarSale.Repositories.OfferRepository;
import com.example.CarSale.Services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        Offer offer_model = modelMapper.map(offerDto, Offer.class);
        return modelMapper.map(offerRepository.save(offer_model), OfferDto.class);
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
    public List<AllOffersWithBrandDto> getAllOffersInfo() {
        return offerRepository.getAllOffersWithInfo();
    }

}
