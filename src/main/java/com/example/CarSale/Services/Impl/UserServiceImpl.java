package com.example.CarSale.Services.Impl;

import com.example.CarSale.Dtos.OfferDto;
import com.example.CarSale.Dtos.UserDto;
import com.example.CarSale.Models.Enums.Role;
import com.example.CarSale.Models.User;
import com.example.CarSale.Repositories.UserRepository;
import com.example.CarSale.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto user) {
        User user_model = modelMapper.map(user, User.class);
        return modelMapper.map(userRepository.save(user_model), UserDto.class);
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
