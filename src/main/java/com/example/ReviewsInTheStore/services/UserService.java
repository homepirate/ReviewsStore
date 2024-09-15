package com.example.ReviewsInTheStore.services;

import com.example.ReviewsInTheStore.services.dtos.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO createUser(UserDTO userDto);
    void deleteUser(UUID userId);
    List<UserDTO> find();
}
