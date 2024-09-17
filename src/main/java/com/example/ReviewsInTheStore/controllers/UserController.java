package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.UserService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.UpdateUserView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserView> getAllUsers() {
        List<UserView> feedbackList = userService.find();
        return feedbackList;
    }

    @PostMapping
    public UserView createUser(@RequestBody UserView userView){
        UserView userView1 = userService.createUser(userView);
        return userView1;
    }

    @PutMapping
    public UserView updateUserEmail(@RequestBody UpdateUserView updateUserView){
        UserView userView = userService.updateUserEmail(updateUserView);
        return userView;
    }
}
