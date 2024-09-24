package com.example.ReviewsInTheStore.services;

import com.example.ReviewsInTheStore.services.dtos.UpdateUserView;
import com.example.ReviewsInTheStore.services.dtos.UserView;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserView createUser(UserView userView);
    Boolean deleteUser(UUID userId);
    List<UserView> find();
    UserView updateUserEmail(UpdateUserView updateUserView);
    UserView getById(UUID id);
}
