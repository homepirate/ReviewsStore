package com.example.CarSale.web.controllers;


import com.example.CarSale.Services.UserService;
import com.example.CarSale.Views.RegUserView;
import com.example.CarSale.Views.UserChange;
import com.example.CarSale.Views.UserView;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("regUserView")
    public RegUserView initUser(){ return new RegUserView();}

    @PostMapping("/reg-new-user")
    public  String regNewUser(@Valid RegUserView regUserView, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if (bindingResult.hasErrors()){
            LOG.log(Level.INFO, "New user with username " + regUserView.getUsername() + "has valid error in reg");
            redirectAttributes.addFlashAttribute("regUserView", regUserView);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.regUserView", bindingResult);
            return "redirect:/users/reg-new-user";
        }
        LOG.log(Level.INFO, "New user with username: " + regUserView.getUsername());
        UserView user = userService.registrationNewUser(regUserView);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
    }

    @GetMapping("/{username}")
    public String userPage(@PathVariable String username, Model model, Principal principal){
        String p_username = (principal != null) ? principal.getName() : "null";

        LOG.log(Level.INFO, p_username + " check profile " + username);
        model.addAttribute("user",userService.getUserByUsername(username));
        model.addAttribute("offers", userService.getUserOffers(username));
        model.addAttribute("principal", principal);
        return "user-page";
    }

    @GetMapping("/reg-new-user")
    public String regNewUSer(){
        LOG.log(Level.INFO, "Check reg page");
        return "add-user";
    }


    @GetMapping("/login")
    public String login(){
        LOG.log(Level.INFO, "Check login page");
        return "login";
    }


    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {
            LOG.log(Level.INFO, "Login error username: " + username);
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }


    @PutMapping("/change-pass")
    public  String changePass(@ModelAttribute UserChange userChange, Model model){
        UserView user = userService.changePassByUser(userChange);
        LOG.log(Level.INFO, "Username: " + userChange.getUsername() + " change password");
        System.out.println(user);
        model.addAttribute("user", user);
        return "redirect:/users/" + user.getUsername();
    }

    @PutMapping("/change-img")
    public String changeImgUrl(@ModelAttribute UserChange userChange, Model model){
        UserView user = userService.changeImgByUser(userChange);
        LOG.log(Level.INFO, "Username: " + userChange.getUsername() + " change img");

        System.out.println(user);
        model.addAttribute("user", user);
        return "redirect:/users/" + user.getUsername();
    }

    @DeleteMapping("/delete/{username}")
    public  String deleteUser(@PathVariable String username, Model model, Principal principal){
        userService.deleteUserByUserName(username);
        LOG.log(Level.INFO, "Username: " + username + "deleted");
        return "redirect:/";
    }

}
