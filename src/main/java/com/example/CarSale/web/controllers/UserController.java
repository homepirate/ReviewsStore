package com.example.CarSale.web.controllers;


import com.example.CarSale.Services.UserService;
import com.example.CarSale.Views.RegUserView;
import com.example.CarSale.Views.UserChange;
import com.example.CarSale.Views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reg-new-user")
    public @ResponseBody String regNewUser(@RequestBody RegUserView userInput, Model model){
        UserView user = userService.registrationNewUser(userInput);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user-page";
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
