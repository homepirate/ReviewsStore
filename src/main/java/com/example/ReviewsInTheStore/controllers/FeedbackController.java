package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackCreateView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.SetAssignmentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private FeedbackService feedbackService;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public FeedbackView createFeedback(@RequestBody FeedbackCreateView feedbackCreateView) {
        FeedbackView createdFeedback = feedbackService.createFeedback(feedbackCreateView);
        return createdFeedback;
    }

    @GetMapping("/{id}")
    public FeedbackView getFeedbackById(@PathVariable UUID id) {
        FeedbackView feedbackView = feedbackService.findById(id);
        return feedbackView;
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable UUID id) {
        feedbackService.deleteFeedback(id);
    }

    @GetMapping
    public List<FeedbackView> getAllFeedback() {
        List<FeedbackView> feedbackList = feedbackService.findAll();
        return feedbackList;
    }

    @PutMapping
    public FeedbackView setAssignment(@RequestBody SetAssignmentView setAssignmentView){
        return feedbackService.setAssignment(setAssignmentView);
    }

    @PutMapping("/{id}")
    public FeedbackView changeStatus(@PathVariable UUID id,  @PathVariable String status){
        FeedbackView feedbackView = feedbackService.changeStatus(id, status);
        return feedbackView;
    }

}
