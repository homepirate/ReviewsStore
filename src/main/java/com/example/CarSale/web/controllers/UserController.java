package com.example.CarSale.web.controllers;


import com.example.CarSale.Services.UserService;
import com.example.CarSale.Views.RegUserView;
import com.example.CarSale.Views.UserChange;
import com.example.CarSale.Views.UserView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/reg-new-user")
    public String regNewUSer(){
        return "add-user";
    }

    @PutMapping("/change-pass")
    public @ResponseBody String changePass(@RequestBody UserChange userChange, Model model){
        UserView user = userService.changePassByUser(userChange);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
    }

    @PutMapping("/change-img")
    public @ResponseBody String changeImgUrl(@RequestBody UserChange userChange, Model model){
        UserView user = userService.changeImgByUser(userChange);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
    }

    @DeleteMapping("/delete/{username}")
    public @ResponseBody String deleteUser(@PathVariable String username, Model model){
        UserView user = userService.deleteUserByUserName(username);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
    }
}
