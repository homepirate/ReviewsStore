package com.example.CarSale.Services.Impl;

import com.example.CarSale.Services.Dtos.OfferDto;
import com.example.CarSale.Services.Dtos.UserDto;
import com.example.CarSale.constants.Enums.Role;
import com.example.CarSale.Models.User;
import com.example.CarSale.Repositories.UserRepository;
import com.example.CarSale.Services.UserService;
import com.example.CarSale.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;


    @Autowired
    public UserServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto user) {
        if (!this.validationUtil.isValid(user)) {
            this.validationUtil
                    .violations(user)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        } else {
            User user_model = modelMapper.map(user, User.class);
            return modelMapper.map(userRepository.save(user_model), UserDto.class);
        }
        return null;
    }

    @Override
    public List<UserDto> findAllByRole(String role) {
        if (role.toLowerCase().equals("admin")){
            return userRepository.findByRoleRole(Role.ADMIN)
                    .stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        }
        else
            return userRepository.findByRoleRole(Role.USER)
                    .stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<OfferDto> getUserOffers(UUID userId) {
        return userRepository.getOffersByUserId(userId)
                .stream().map((offer) -> modelMapper.map(offer, OfferDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto changePassword(UUID userId, String newPass) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(newPass);
            return modelMapper.map(userRepository.save(user), UserDto.class);
        }
        else {
            return null;
        }
    }

    @Override
    public UserDto getByUserName(String username) {
        User user = userRepository.findByUsername(username);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto changeImgUrl(UUID id, String newUrl) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setImageUrl(newUrl);
            return modelMapper.map(userRepository.save(user), UserDto.class);
        }
        else {
            return null;
        }
    }
}
