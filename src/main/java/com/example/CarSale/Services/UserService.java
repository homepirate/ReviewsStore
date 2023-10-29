package com.example.CarSale.Services;

import com.example.CarSale.Services.Dtos.OfferDto;
import com.example.CarSale.Services.Dtos.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService extends GeneralSevice{
    UserDto createUser(UserDto user);

    List<UserDto> findAllByRole(String role);

    void deleteUser(UUID userId);

    List<UserDto> getAll();

    List<OfferDto> getUserOffers(UUID userId);

    UserDto changePassword(UUID userId, String newPass);

    UserDto getByUserName(String username);
}
