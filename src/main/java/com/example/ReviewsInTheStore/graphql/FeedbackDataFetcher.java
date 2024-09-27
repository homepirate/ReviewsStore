package com.example.ReviewsInTheStore.graphql;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class FeedbackDataFetcher {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackDataFetcher(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @DgsQuery
    public List<FeedbackView> allFeedbacks() {
        List<FeedbackView> feedbackViews = feedbackService.findAll();
        System.out.println(feedbackViews);
        return feedbackViews;

    }

    @DgsQuery
    public FeedbackView feedbackById(@InputArgument String id) {
        UUID feedbackId = UUID.fromString(id);
        return feedbackService.findById(feedbackId);
    }

}