package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.Feedback;
import com.example.ReviewsInTheStore.models.User;
import com.example.ReviewsInTheStore.repositories.UserRepository;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.UserService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.UpdateUserView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private FeedbackService feedbackService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, FeedbackService feedbackService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.feedbackService = feedbackService;
    }

    @Override
    public UserView createUser(UserView userView) {
        User user = modelMapper.map(userView, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserView.class);
    }

    @Override
    public Boolean deleteUser(UUID userId) {
        Optional<User> UserOpt = userRepository.findById(userId);
        if (UserOpt.isEmpty()){
            return Boolean.FALSE;
        }
        userRepository.deleteById(userId);
        return Boolean.TRUE;
    }

    @Override
    public List<UserView> find() {
        List<UserView> userViews = new ArrayList<>();
        List<User> users =  userRepository.findAll();

        for (User user:  users){
            List<FeedbackView> feedbackViews = new ArrayList<>();
            UserView userView = modelMapper.map(user, UserView.class);
            for (Feedback feedback: user.getFeedbacks()){
                feedbackViews.add(feedbackService.findById(feedback.getId()));
            }
            userView.setFeedbacks(feedbackViews);
            userViews.add(userView);
        }
        return userViews;
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

    @Override
    public UserView getById(UUID id) {
        Optional<User> userOp = userRepository.findById(id);
        if (userOp.isEmpty()){
            return null;
        }
        User user = userOp.get();
        return modelMapper.map(user, UserView.class);
    }
}
