package com.example.CarSale.Services;

import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.Views.CreateOfferFromUser;
import com.example.CarSale.Services.Dtos.OfferDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OfferService  extends GeneralSevice{
    OfferDto createOffer(OfferDto offerDto);



    List<OfferDto> getAll();

    void deleteOffer(UUID offerId);

    List<OfferDto> getOfferByEngine(String engine);
    List<OfferDto> getOfferByTransmission(String transmission);

    OfferDto changePrice(UUID offerId, int newPrice);

    Map<String, LocalDateTime> getCreatedandModifiedInfo(UUID offerId);
    AllOfferWithBrandView createOfferByUser(CreateOfferFromUser offerModel);
    List<AllOfferWithBrandView> getAllOffersInfo();
    AllOfferWithBrandView getOfferById(UUID offerId);
     List<AllOfferWithBrandView> getFilteredOffers(Optional<List<String>> engines, Optional<List<String>> transmissions, String model);

    }
