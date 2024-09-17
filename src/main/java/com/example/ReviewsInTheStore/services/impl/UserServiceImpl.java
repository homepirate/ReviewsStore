package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.User;
import com.example.ReviewsInTheStore.repositories.UserRepository;
import com.example.ReviewsInTheStore.services.UserService;
import com.example.ReviewsInTheStore.services.dtos.UpdateUserView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserView createUser(UserView userView) {
        User user = modelMapper.map(userView, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserView.class);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserView> find() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserView.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserView updateUserEmail(UpdateUserView updateUserView){
        Optional<User> userOp = userRepository.findById(updateUserView.getId());
        if (userOp.isEmpty()){
            return null;
        }
        User user = userOp.get();
        user.setEmail(updateUserView.getEmail());
        return modelMapper.map(userRepository.save(user), UserView.class);
    }
}
