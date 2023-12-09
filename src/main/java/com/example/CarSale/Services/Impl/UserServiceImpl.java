package com.example.CarSale.Services.Impl;

import com.example.CarSale.Repositories.UserRoleRepository;
import com.example.CarSale.Services.Dtos.OfferDto;
import com.example.CarSale.Services.Dtos.UserDto;
import com.example.CarSale.Services.Dtos.UserRoleDto;
import com.example.CarSale.Services.UserRoleService;
import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.Views.RegUserView;
import com.example.CarSale.Views.UserChange;
import com.example.CarSale.Views.UserView;
import com.example.CarSale.constants.Enums.Role;
import com.example.CarSale.Models.User;
import com.example.CarSale.Repositories.UserRepository;
import com.example.CarSale.Services.UserService;
import com.example.CarSale.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserRoleService userRoleService;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
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
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setActive(Boolean.FALSE);
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

//    @Override
//    public List<OfferDto> getUserOffers(UUID userId) {
//        return userRepository.getOffersByUserId(userId)
//                .stream().map((offer) -> modelMapper.map(offer, OfferDto.class)).collect(Collectors.toList());
//    }

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
        Optional<User> user = userRepository.findByUsername(username);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserView getUserByUsername(String username) {
        return modelMapper.map(this.getByUserName(username), UserView.class);
    }

    @Override
    public UserView changeImgByUser(UserChange userChange) {
        UserDto userDto = this.getByUserName(userChange.getUsername());
        UserDto userDto_afterChange = this.changeImgUrl(userDto.getId(), userChange.getValue());
        return modelMapper.map(userDto_afterChange, UserView.class);
    }

    @Override
    public UserView changePassByUser(UserChange userChange) {
        UserDto userDto = this.getByUserName(userChange.getUsername());
        UserDto userDto_afterChange = this.changePassword(userDto.getId(), userChange.getValue());
        return modelMapper.map(userDto_afterChange, UserView.class);

    }

    @Override
    public UserView registrationNewUser(RegUserView regUserView) {
        UserDto userDto = modelMapper.map(regUserView, UserDto.class);
        userDto.setImageUrl("/img/149071.png");
        userDto.setActive(Boolean.TRUE);
        userDto.setPassword(passwordEncoder.encode(regUserView.getPassword()));
        userDto.setRole(userRoleService.getByRole(Role.USER));
        UserDto userDto_afterCreate = this.createUser(userDto);
        return modelMapper.map(userDto_afterCreate, UserView.class);
    }

    @Override
    public UserView deleteUserByUserName(String username) {
        UserDto userDto = this.getByUserName(username);
        this.deleteUser(userDto.getId());
        return modelMapper.map(this.getByUserName(username), UserView.class);
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

    @Override
    public List<AllOfferWithBrandView> getUserOffers(String username) {
        return userRepository.getAllUserOffers(username);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }
}
