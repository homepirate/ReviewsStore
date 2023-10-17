package com.example.CarSale.Services;

import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Dtos.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto createUser(UserDto user);

    List<UserDto> findAllByRole(String role);

    List<UserDto> getAll();

    List<OfferDto> getUserOffers(UUID userId);
}
