package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.UserService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.UpdateUserView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import com.example.ReviewsInTheStore.services.dtos.UserViewResource;
import com.example.contract_first.controllers.interfacies.UserAPI;
import com.example.contract_first.dto.FeedbackResponse;
import com.example.contract_first.dto.UserRequest;
import com.example.contract_first.dto.UserResponse;
import com.example.contract_first.dto.UserUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UserController implements UserAPI {

    private UserService userService;
    private ModelMapper modelMapper;


    @Autowired
    public void setUserService(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<CollectionModel<EntityModel<UserResponse>>> getAllUsers() {
        List<UserView> userViews = userService.find();

        List<UserResponse> usersResp = userViews.stream()
                .map(this::mapViewToResponse)
                .toList();

        List<EntityModel<UserResponse>> users = usersResp.stream()
                .map(this::createUserResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(users,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withSelfRel()));
    }

    public ResponseEntity<EntityModel<UserResponse>> createUser(@RequestBody UserRequest request) {

        UserView userView = modelMapper.map(request, UserView.class);
        UserView createdUser = userService.createUser(userView);

        UserResponse userResponse = new UserResponse(
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getEmail(),
                new ArrayList<>());
        
        EntityModel<UserResponse> resource = createUserResource(userResponse);

        return ResponseEntity.created(resource.getRequiredLink("self").toUri()).body(resource);
    }



    public ResponseEntity<EntityModel<UserResponse>> updateUserEmail(@RequestBody UserUpdateRequest request) {

        UpdateUserView updateUserView = modelMapper.map(request, UpdateUserView.class);

        UserView updatedUser = userService.updateUserEmail(updateUserView);

        UserResponse userResponse = mapViewToResponse(updatedUser);

        if (userResponse != null) {
            EntityModel<UserResponse> resource = createUserResource(userResponse);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<EntityModel<UserResponse>> getUserById(@PathVariable UUID id) {
        UserView userView = userService.getById(id);
        UserResponse userResponse = mapViewToResponse(userView);
        if (userResponse != null) {
            EntityModel<UserResponse> resource = createUserResource(userResponse);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private UserViewResource createUserResource(UserResponse userResponse) {
        Map<String, Object> actions = Map.of(
                "update", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUserEmail(new UserUpdateRequest(userResponse.id(), ""))).toUri().toString(),
                        "method", "PUT",
                        "accept", "application/json"
                ),
                "delete", Map.of(
                        "href", WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deleteUser(userResponse.id())).toUri().toString(),
                        "method", "DELETE"
                )
        );

        return (UserViewResource) new UserViewResource(userResponse, actions)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(userResponse.id())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users"));
    }

    UserResponse mapViewToResponse(UserView userView){
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        if (userView.getFeedbacks() != null) {
            for (FeedbackView feedbackView : userView.getFeedbacks()) {
                feedbackResponses.add(new FeedbackResponse(
                        feedbackView.getMessage(),
                        feedbackView.getUserId(),
                        feedbackView.getId(),
                        feedbackView.getAssignmentId(),
                        feedbackView.getStatus()
                ));
            }
        }
        UserResponse userResponse = new UserResponse(
                userView.getId(),
                userView.getName(),
                userView.getEmail(),
                feedbackResponses
        );
        return userResponse;
    }

}