package com.example.ReviewsInTheStore.graphql;

import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.UserService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent

public class UserDataFetcher {


    private final UserService userService;

    @Autowired
    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @DgsQuery
    public List<UserView> allUsers() {
        List<UserView> userViews = userService.find();
        return userViews;

    }

    @DgsQuery
    public UserView userById(@InputArgument String id) {
        UUID userId = UUID.fromString(id);
        return userService.getById(userId);
    }
}

