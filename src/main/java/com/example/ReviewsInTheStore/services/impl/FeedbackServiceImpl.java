package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.Feedback;
import com.example.ReviewsInTheStore.repositories.FeedbackRepository;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackDTO;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private ModelMapper modelMapper;
    private FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO feedbackDto) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackDTO.class);
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

    public List<FeedbackView> findAll(){
        return find().stream().map(feedbackDTO -> modelMapper.map(feedbackDTO, FeedbackView.class))
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackDTO findById(UUID feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        return modelMapper.map(feedback, FeedbackDTO.class);
    }
}
