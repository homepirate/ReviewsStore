package com.example.ReviewsInTheStore.services;


import com.example.ReviewsInTheStore.services.dtos.FeedbackDTO;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {

    FeedbackDTO createFeedback(FeedbackDTO feedback);
    void deleteFeedback(UUID feedbackId);
    List<FeedbackDTO> find();
    List<FeedbackView> findAll();
    FeedbackDTO findById(UUID feedbackId);
}
