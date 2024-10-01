package com.example.ReviewsInTheStore.graphql;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackCreateView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.SetAssignmentView;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
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
        return feedbackViews;

    }

    @DgsQuery
    public FeedbackView feedbackById(@InputArgument String id) {
        UUID feedbackId = UUID.fromString(id);
        return feedbackService.findById(feedbackId);
    }


    @DgsMutation
    public FeedbackView changeStatus(@InputArgument String id, @InputArgument String status) {
        UUID feedbackId = UUID.fromString(id);
        return feedbackService.changeStatus(feedbackId, status);
    }

    @DgsMutation
    public FeedbackView setAssignment(@InputArgument SetAssignmentView setAssignmentView) {
        UUID employeeId = setAssignmentView.getEmployeeId();
        UUID feedbackId = setAssignmentView.getFeedbackId();
        return feedbackService.setAssignment(new SetAssignmentView(employeeId, feedbackId));
    }

    @DgsMutation
    public FeedbackView createFeedback(@InputArgument FeedbackCreateView feedbackCreateView) {
        FeedbackCreateView createView = new FeedbackCreateView(feedbackCreateView.getMessage(),
                feedbackCreateView.getUserId(),
                feedbackCreateView.getStatus());
        return feedbackService.createFeedback(createView);
    }

}