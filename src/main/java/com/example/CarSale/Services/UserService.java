package com.example.CarSale.Services;

import com.example.CarSale.Services.Dtos.OfferDto;
import com.example.CarSale.Services.Dtos.UserDto;
import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.Views.RegUserView;
import com.example.CarSale.Views.UserChange;
import com.example.CarSale.Views.UserView;

import java.util.List;
import java.util.UUID;

public interface UserService extends GeneralSevice{
    UserDto createUser(UserDto user);

    List<UserDto> findAllByRole(String role);

    void deleteUser(UUID userId);

    List<UserDto> getAll();

//    List<OfferDto> getUserOffers(UUID userId);

    UserDto changePassword(UUID userId, String newPass);

    UserDto getByUserName(String username);

    UserView changeImgByUser(UserChange userChange);

    UserView changePassByUser(UserChange userChange);

    UserView registrationNewUser(RegUserView regUserView);

    void deleteUserByUserName(String username);

    UserView getUserByUsername(String username);

    List<AllOfferWithBrandView> getUserOffers(String username);
}
