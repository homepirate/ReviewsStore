package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.config.RabbitMQConfiguration;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackCreateView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.SetAssignmentView;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private FeedbackService feedbackService;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService, RabbitTemplate rabbitTemplate) {
        this.feedbackService = feedbackService;
        this.rabbitTemplate = rabbitTemplate;
    }

//    @PostMapping
//    public FeedbackView createFeedback(@RequestBody FeedbackCreateView feedbackCreateView) {
//        FeedbackView createdFeedback = feedbackService.createFeedback(feedbackCreateView);
//        return createdFeedback;
//    }

    @PostMapping
    public EntityModel<FeedbackView> createFeedback(@RequestBody FeedbackCreateView feedbackCreateView) {
        FeedbackView createdFeedback = feedbackService.createFeedback(feedbackCreateView);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.exchangeName, "feedback.created",
                createdFeedback);

        return EntityModel.of(createdFeedback,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(createdFeedback.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(createdFeedback.getId(), null)).withRel("changeStatus"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).setAssignment(null)).withRel("setAssignment"));
    }

//    @GetMapping("/{id}")
//    public FeedbackView getFeedbackById(@PathVariable UUID id) {
//        FeedbackView feedbackView = feedbackService.findById(id);
//        return feedbackView;
//    }

    @GetMapping("/{id}")
    public EntityModel<FeedbackView> getFeedbackById(@PathVariable UUID id) {
        FeedbackView feedbackView = feedbackService.findById(id);
        return EntityModel.of(feedbackView,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(id, null)).withRel("changeStatus"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).setAssignment(null)).withRel("setAssignment"));
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable UUID id) {
        feedbackService.deleteFeedback(id);
    }

//    @GetMapping
//    public List<FeedbackView> getAllFeedback() {
//        List<FeedbackView> feedbackList = feedbackService.findAll();
//        return feedbackList;
//    }

    @GetMapping
    public CollectionModel<EntityModel<FeedbackView>> getAllFeedback() {
        List<EntityModel<FeedbackView>> feedbackList = feedbackService.findAll().stream()
                .map(feedbackView -> EntityModel.of(feedbackView,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(feedbackView.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(feedbackView.getId(), null)).withRel("changeStatus"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).setAssignment(null)).withRel("setAssignment")))
                .collect(Collectors.toList());

        return CollectionModel.of(feedbackList,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withSelfRel());
    }

//    @PutMapping
//    public FeedbackView setAssignment(@RequestBody SetAssignmentView setAssignmentView){
//        return feedbackService.setAssignment(setAssignmentView);
//    }

    @PutMapping("/set-assignment")
    public EntityModel<FeedbackView> setAssignment(@RequestBody SetAssignmentView setAssignmentView) {
        FeedbackView assignedFeedback = feedbackService.setAssignment(setAssignmentView);
        return EntityModel.of(assignedFeedback,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(assignedFeedback.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(assignedFeedback.getId(), null)).withRel("changeStatus"));
    }

//    @PutMapping("/{id}")
//    public FeedbackView changeStatus(@PathVariable UUID id,  @PathVariable String status){
//        FeedbackView feedbackView = feedbackService.changeStatus(id, status);
//        return feedbackView;
//    }

    @PutMapping("/{id}")
    public EntityModel<FeedbackView> changeStatus(@PathVariable UUID id, @RequestParam String status) {
        FeedbackView updatedFeedback = feedbackService.changeStatus(id, status);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.exchangeName, "feedback.statusChanged",
                updatedFeedback);

        return EntityModel.of(updatedFeedback,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).setAssignment(null)).withRel("setAssignment"));
    }

}
