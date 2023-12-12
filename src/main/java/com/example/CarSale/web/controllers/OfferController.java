package com.example.CarSale.web.controllers;

import com.example.CarSale.Services.BrandService;
import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.Views.BrandView;
import com.example.CarSale.Views.CreateOfferFromUser;
import com.example.CarSale.Services.OfferService;
import com.example.CarSale.utils.UtilsForFront;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private OfferService offerService;
    private BrandService brandService;
    private UtilsForFront utilsForFront;

    @Autowired
    public void setUtilsForFront(UtilsForFront utilsForFront) {
        this.utilsForFront = utilsForFront;
    }

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }


    @ModelAttribute("createOffer")
    public CreateOfferFromUser initOffer(){ return new CreateOfferFromUser();}

    @GetMapping("")
    public String getAll(Model model, Principal principal){
        String username = (principal != null) ? principal.getName() : "null";

        LOG.log(Level.INFO, "Show all offers for " + username);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<AllOfferWithBrandView> allOffers = offerService.getAllOffersInfo();
        stopWatch.stop();
        System.out.println("Время выполнения getAll: " + stopWatch.getTotalTimeMillis() + " мс");
        allOffers.forEach(System.out::println);
        model.addAttribute("offers", allOffers);
        model.addAttribute("principal", principal);
        return "all-offers";
    }

    @PostMapping("/create-offer")
    public String createOffer(@Valid CreateOfferFromUser offerInput, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal){
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "New user with username " + principal.getName() + "has valid error in create offer");

            redirectAttributes.addFlashAttribute("createOffer", offerInput);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createOffer", bindingResult);
            return "redirect:/offers/create-offer";
        }
        AllOfferWithBrandView createdOffer = offerService.createOfferByUser(offerInput);
        LOG.log(Level.INFO, "Username: " + principal.getName() + " created new offer");
//        System.out.println(createdOffer);
//        model.addAttribute("createdOffer", createdOffer);
        return "redirect:/offers";
    }

    @GetMapping("/create-offer")
    public String createOffer(Model model, Principal principal){
        LOG.log(Level.INFO, "Username: " + principal.getName() + "watch create offer page");
        model.addAttribute("Brands", brandService.getAll());
        model.addAttribute("Engines", utilsForFront.getAllEngine());
        model.addAttribute("Transm", utilsForFront.getAllTransmission());
        return "offer-add";
    }



    @GetMapping("/filtered")
    public String filteredoffers(@RequestParam Optional<List<String>> engine, @RequestParam Optional<List<String>> transm,
                                 @RequestParam(required = false) String carmodel, Model model, Principal principal) {
        List<AllOfferWithBrandView> result = offerService.getFilteredOffers(engine, transm, carmodel);
//        result.forEach(System.out::println);
        String username = (principal != null) ? principal.getName() : "null";
        LOG.log(Level.INFO, "Username: " + username + "get filtered offers: " + engine.toString() + ' ' + transm.toString()+ ' ' + carmodel);
        model.addAttribute("offers", result);
        model.addAttribute("principal", principal);
        return "all-offers";

    }


    @DeleteMapping("/delete/{offerId}")
    public String deleteOffer(@PathVariable UUID offerId, Principal principal) {

        String username=principal.getName();
        AllOfferWithBrandView offerDetails = offerService.getOfferById(offerId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (offerDetails.getUsername().equals(username)) {
            offerService.deleteOffer(offerId);
            LOG.log(Level.INFO, "Username: " + username + " delete offer: " + offerId);
            return "redirect:/users/" + username;
        }else if(isAdmin){
            offerService.deleteOffer(offerId);
            LOG.log(Level.INFO, "Offer: " + offerId + " deleted by ADMIN: " + authentication.getName());
            return  "redirect:/offers";
        }

        return "redirect:/error";
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
