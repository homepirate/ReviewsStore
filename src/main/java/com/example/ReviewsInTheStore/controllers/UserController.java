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

    @GetMapping
    public CollectionModel<EntityModel<UserView>> getAllUsers() {
        List<EntityModel<UserView>> users = userService.find().stream()
                .map(userView -> EntityModel.of(userView,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(userView.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUserEmail(null)).withRel("updateEmail")))
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @PostMapping
    public EntityModel<UserView> createUser(@RequestBody UserView userView) {
        UserView createdUser = userService.createUser(userView);
        return EntityModel.of(createdUser,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUserEmail(new UpdateUserView())).withRel("updateEmail"));
    }

    @PutMapping
    public EntityModel<UserView> updateUserEmail(@RequestBody UpdateUserView updateUserView) {
        UserView updatedUser = userService.updateUserEmail(updateUserView);
        return EntityModel.of(updatedUser,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(updatedUser.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

    @GetMapping("/{id}")
    public EntityModel<UserView> getUserById(@PathVariable UUID id) {
        UserView userView = userService.getById(id);
        return EntityModel.of(userView,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUserEmail(new UpdateUserView(userView.getId(), "base@email.com"))).withRel("updateEmail"));
    }
}
