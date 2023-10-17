package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Dtos.UserDto;
import com.example.CarSale.Models.Offer;
import com.example.CarSale.Models.User;
import com.example.CarSale.Repositories.OfferRepository;
import com.example.CarSale.Services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
