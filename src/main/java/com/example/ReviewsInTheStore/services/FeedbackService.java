package com.example.ReviewsInTheStore.services;


import com.example.ReviewsInTheStore.services.dtos.*;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {

    FeedbackView createFeedback(FeedbackCreateView feedback);
    void deleteFeedback(UUID feedbackId);
    List<FeedbackDTO> find();
    List<FeedbackView> findAll();
    FeedbackView findById(UUID feedbackId);
    FeedbackView setAssignment(SetAssignmentView setAssignmentView);
    FeedbackView changeStatus(UUID id, String status);
    FeedbackMessage getMessageForRabbit(FeedbackView feedbackView);
}
