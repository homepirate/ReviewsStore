package com.example.CarSale.web.controllers;

import com.example.CarSale.Views.AllOffersWithBrandView;
import com.example.CarSale.Services.Dtos.CreateOfferFromUser;
import com.example.CarSale.Services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private OfferService offerService;

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/create-offer")
    public ResponseEntity<AllOffersWithBrandView> createOffer(@RequestBody CreateOfferFromUser offerInput){
        AllOffersWithBrandView createdOffer = offerService.createOfferByUser(offerInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    @GetMapping("")
    public ResponseEntity<List<AllOffersWithBrandView>> getAllInfoOffers(){
        return ResponseEntity.status(HttpStatus.OK).body(offerService.getAllOffersInfo());
    }
//    @GetMapping("/get-all")
//    public ResponseEntity<List<OfferDto>> getAllOffers(){
//        List<OfferDto> offerDtos = offerService.getAll();
//        return ResponseEntity.status(HttpStatus.OK).body(offerDtos);
//    }

}
