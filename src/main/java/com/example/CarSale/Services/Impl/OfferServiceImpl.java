package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.AllOffersWithBrandDto;
import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Dtos.UserDto;
import com.example.CarSale.Models.Enums.Engine;
import com.example.CarSale.Models.Enums.Transmission;
import com.example.CarSale.Models.Offer;
import com.example.CarSale.Models.User;
import com.example.CarSale.Repositories.OfferRepository;
import com.example.CarSale.Services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;

    private ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

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
}
