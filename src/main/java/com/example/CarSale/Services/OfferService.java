package com.example.CarSale.Services;

import com.example.CarSale.Dtos.AllOffersWithBrandDto;
import com.example.CarSale.Dtos.OfferDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OfferService  extends GeneralSevice{
    OfferDto createOffer(OfferDto offerDto);

    List<OfferDto> getAll();

    void deleteOffer(UUID offerId);

    List<OfferDto> getOfferByEngine(String engine);
    List<OfferDto> getOfferByTransmission(String transmission);

    List<AllOffersWithBrandDto> getAllOffersInfo();

    OfferDto changePrice(UUID offerId, int newPrice);

    Map<String, LocalDateTime> getCreatedandModifiedInfo(UUID offerId);

}
