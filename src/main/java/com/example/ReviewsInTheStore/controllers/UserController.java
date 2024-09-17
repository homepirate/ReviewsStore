package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.models.User;
import com.example.ReviewsInTheStore.services.UserService;
import com.example.ReviewsInTheStore.services.dtos.UpdateUserView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public List<UserView> getAllUsers() {
//        List<UserView> feedbackList = userService.find();
//        return feedbackList;
//    }
//
@GetMapping
public CollectionModel<EntityModel<UserView>> getAllUsers() {
    List<EntityModel<UserView>> users = userService.find().stream()
            .map(userView -> EntityModel.of(userView,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(userView.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users")))
            .collect(Collectors.toList());

    return CollectionModel.of(users,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withSelfRel());
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

    @GetMapping("/{id}")
    public UserView getUserById(@PathVariable UUID id){
        UserView userView = userService.getById(id);
        return userView;
    }
}
