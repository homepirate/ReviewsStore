package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.UserService;
import com.example.ReviewsInTheStore.services.dtos.UpdateUserView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import com.example.ReviewsInTheStore.services.dtos.UserViewResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    public ResponseEntity<CollectionModel<EntityModel<UserView>>> getAllUsers() {
        List<EntityModel<UserView>> users = userService.find().stream()
                .map(userView -> createUserResource(userView))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(users,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserView>> createUser(@RequestBody UserView userView) {
        UserView createdUser = userService.createUser(userView);
        EntityModel<UserView> resource = createUserResource(createdUser);

        return ResponseEntity.created(resource.getRequiredLink("self").toUri()).body(resource);
    }

    @PutMapping
    public ResponseEntity<EntityModel<UserView>> updateUserEmail(@RequestBody UpdateUserView updateUserView) {
        UserView updatedUser = userService.updateUserEmail(updateUserView);
        if (updatedUser != null) {
            EntityModel<UserView> resource = createUserResource(updatedUser);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserView>> getUserById(@PathVariable UUID id) {
        UserView userView = userService.getById(id);
        if (userView != null) {
            EntityModel<UserView> resource = createUserResource(userView);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private UserViewResource createUserResource(UserView userView) {
        Map<String, Object> actions = Map.of(
                "update", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUserEmail(new UpdateUserView(userView.getId(), ""))).toUri().toString(),
                        "method", "PUT",
                        "accept", "application/json"
                ),
                "delete", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deleteUser(userView.getId())).toUri().toString(),
                        "method", "DELETE"
                )
        );

        return (UserViewResource) new UserViewResource(userView, actions)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(userView.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

}