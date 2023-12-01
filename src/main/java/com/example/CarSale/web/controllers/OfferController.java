package com.example.CarSale.web.controllers;

import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.Views.BrandView;
import com.example.CarSale.Views.CreateOfferFromUser;
import com.example.CarSale.Services.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private OfferService offerService;
    private BrandService brandService;

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    public String getAll(Model model){
        List<AllOfferWithBrandView> allOffers = offerService.getAllOffersInfo();
        allOffers.forEach(System.out::println);
        model.addAttribute("offers", allOffers);
        return "all-offers";
    }

    @PostMapping("/create-offer")
    public String createOffer(@Valid CreateOfferFromUser offerInput, Model model){
        AllOfferWithBrandView createdOffer = offerService.createOfferByUser(offerInput);
        System.out.println(createdOffer);
        model.addAttribute("createdOffer", createdOffer);
        return "all-offers";
    }

    @GetMapping("/create-offer")
    public String createOffer(Model model){
        model.addAttribute("Brands", brandService.getAll());
        return "offer-add";
    }



    @GetMapping("/filtered")
    public String filteredoffers(@RequestParam Optional<List<String>> engine, @RequestParam Optional<List<String>> transm,
                                 @RequestParam(required = false) String carmodel, Model model) {
        List<AllOfferWithBrandView> result = offerService.getFilteredOffers(engine, transm, carmodel);
        result.forEach(System.out::println);
        model.addAttribute("offers", result);
        return "all-offers";

    }

//
//    @GetMapping("/get-all")
//    public ResponseEntity<List<AllOfferWithBrandView>> getAllInfoOffers(){
//        return ResponseEntity.status(HttpStatus.OK).body(offerService.getAllOffersInfo());
//    }
//    @GetMapping("/transmissions/{transm}")
//    public @ResponseBody String getByTransmission(@PathVariable String transm, Model model){
//        List<AllOfferWithBrandView> allOfferByTransmission = offerService.getOfferByTransmissionToUser(transm);
//        allOfferByTransmission.forEach(System.out::println);
//        model.addAttribute("allOffersByTransmission", allOfferByTransmission);
//        return "all-offers-by-transmission";
//    }
//
//    @GetMapping("/engine/{eng}")
//    public @ResponseBody String getByEngine(@PathVariable String eng, Model model){
//        List<AllOfferWithBrandView> allOfferByEngine = offerService.getOfferByEngineToUser(eng);
//        allOfferByEngine.forEach(System.out::println);
//        model.addAttribute("allOffersByEngine", allOfferByEngine);
//        return "all-offers-by-engine";
//    }



//
//    @PostMapping("/create-offer")
//    public ResponseEntity<AllOfferWithBrandView> createOffer(@RequestBody CreateOfferFromUser offerInput){
//        AllOfferWithBrandView createdOffer = offerService.createOfferByUser(offerInput);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
//    }
}
