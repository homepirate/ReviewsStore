package com.example.ReviewsInTheStore.services;


import com.example.ReviewsInTheStore.services.dtos.FeedbackCreateView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackDTO;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {

    FeedbackView createFeedback(FeedbackCreateView feedback);
    void deleteFeedback(UUID feedbackId);
    List<FeedbackDTO> find();
    List<FeedbackView> findAll();
    FeedbackView findById(UUID feedbackId);
}
