package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.Feedback;
import com.example.ReviewsInTheStore.models.User;
import com.example.ReviewsInTheStore.repositories.FeedbackRepository;
import com.example.ReviewsInTheStore.repositories.UserRepository;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackCreateView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackDTO;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private ModelMapper modelMapper;
    private FeedbackRepository feedbackRepository;
    private UserRepository userRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public FeedbackView createFeedback(FeedbackCreateView feedbackCreateView) {
        Optional<User> user = userRepository.findById(feedbackCreateView.getUserId());
        Feedback feedback = modelMapper.map(feedbackCreateView, Feedback.class);
        if (user.isEmpty()) {
            return null;
        }
        feedback.setSubmittedBy(user.get());
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackView.class);
    }

    @Override
    public void deleteFeedback(UUID feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    @Override
    public List<FeedbackDTO> find() {
        return feedbackRepository.findAll().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackView> findAll(){
        List<FeedbackView> feedbackViews = new ArrayList<>();
        for (FeedbackDTO feedbackDTO : find()) {
            FeedbackView feedbackView = modelMapper.map(feedbackDTO, FeedbackView.class);
            feedbackView.setUserId(feedbackDTO.getSubmittedBy().getId());
            feedbackViews.add(feedbackView);
        }
        return feedbackViews;
    }

    @Override
    public FeedbackView findById(UUID feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isEmpty()){
            return null;
        }
        Feedback feedbackModel = feedback.get();
        FeedbackView feedbackView =  modelMapper.map(feedbackModel, FeedbackView.class);
        feedbackView.setUserId(feedbackModel.getSubmittedBy().getId());
        return feedbackView;
    }
}
