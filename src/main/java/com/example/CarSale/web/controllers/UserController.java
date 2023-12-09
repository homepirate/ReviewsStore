package com.example.CarSale.web.controllers;


import com.example.CarSale.Services.UserService;
import com.example.CarSale.Views.RegUserView;
import com.example.CarSale.Views.UserChange;
import com.example.CarSale.Views.UserView;
import jakarta.validation.Valid;
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
            redirectAttributes.addFlashAttribute("regUserView", regUserView);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.regUserView", bindingResult);
            return "redirect:/users/reg-new-user";
        }
        UserView user = userService.registrationNewUser(regUserView);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
    }

    @GetMapping("/{username}")
    public String userPage(@PathVariable String username, Model model, Principal principal){
        model.addAttribute("user",userService.getUserByUsername(username));
        model.addAttribute("offers", userService.getUserOffers(username));
        model.addAttribute("principal", principal);
        return "user-page";
    }

    @GetMapping("/reg-new-user")
    public String regNewUSer(){
        return "add-user";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }


    @PutMapping("/change-pass")
    public  String changePass(@RequestBody UserChange userChange, Model model){
        UserView user = userService.changePassByUser(userChange);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
    }

    @PutMapping("/change-img")
    public String changeImgUrl(@RequestBody UserChange userChange, Model model){
        UserView user = userService.changeImgByUser(userChange);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
    }

    @DeleteMapping("/delete/{username}")
    public  String deleteUser(@PathVariable String username, Model model, Principal principal){
        userService.deleteUserByUserName(username);
        return "redirect:/";
    }

}
