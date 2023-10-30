package com.example.CarSale.web.controllers;


import com.example.CarSale.Services.UserService;
import com.example.CarSale.Views.RegUserView;
import com.example.CarSale.Views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
