package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.config.RabbitMQConfiguration;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.AssignmentView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackCreateView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.SetAssignmentView;
import com.example.contract_first.controllers.interfacies.FeedbackAPI;
import com.example.contract_first.dto.AssignmentResponse;
import com.example.contract_first.dto.FeedbackCreateRequest;
import com.example.contract_first.dto.FeedbackResponse;
import com.example.contract_first.dto.SetAssignmentRequest;
import com.example.contract_first.exception.InvalidArgumentException;
import com.example.contract_first.exception.ResourceNotFoundException;
import com.example.contract_first.exception.StatusResponse;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController implements FeedbackAPI {

    private FeedbackService feedbackService;
    private RabbitTemplate rabbitTemplate;
    private ModelMapper modelMapper;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService, RabbitTemplate rabbitTemplate, ModelMapper modelMapper) {
        this.feedbackService = feedbackService;
        this.rabbitTemplate = rabbitTemplate;
        this.modelMapper = modelMapper;
    }

//    @PostMapping
//    public FeedbackView createFeedback(@RequestBody FeedbackCreateView feedbackCreateView) {
//        FeedbackView createdFeedback = feedbackService.createFeedback(feedbackCreateView);
//        return createdFeedback;
//    }



//    @GetMapping("/{id}")
//    public FeedbackView getFeedbackById(@PathVariable UUID id) {
//        FeedbackView feedbackView = feedbackService.findById(id);
//        return feedbackView;
//    }

    @GetMapping("/{id}")
    public EntityModel<FeedbackResponse> getFeedbackById(@PathVariable UUID id) {
        FeedbackView feedbackView = feedbackService.findById(id);
        FeedbackResponse feedbackResponse = mapViewToResponse(feedbackView);
        return EntityModel.of(feedbackResponse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(id, null)).withRel("changeStatus"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).setAssignment(null)).withRel("setAssignment"));
    }

    @Override
    @PostMapping
    public EntityModel<FeedbackResponse> createFeedback(FeedbackCreateRequest request) {
        FeedbackCreateView feedbackCreateView= modelMapper.map(request, FeedbackCreateView.class);
        FeedbackView createdFeedback = null;
        try {
            createdFeedback = feedbackService.createFeedback(feedbackCreateView);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Invalid feedback data: " + e.getMessage());
        }

        if (createdFeedback == null) {
            throw new ResourceNotFoundException("Feedback could not be created.");
        }

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.exchangeName, "feedback.created",
                feedbackService.getMessageForRabbit(createdFeedback));

        FeedbackResponse feedbackResponse = mapViewToResponse(createdFeedback);

        return EntityModel.of(feedbackResponse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(feedbackResponse.id())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(feedbackResponse.id(), null)).withRel("changeStatus"),
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
    public CollectionModel<EntityModel<FeedbackResponse>> getAllFeedback() {
        List<FeedbackView> feedbackViewList = feedbackService.findAll();
        List<FeedbackResponse> feedbackResponses = feedbackViewList.stream().map(this::mapViewToResponse).toList();

        List<EntityModel<FeedbackResponse>> feedbackList = feedbackResponses.stream()
                .map(feedback -> EntityModel.of(feedback,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(feedback.id())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(feedback.id(), null)).withRel("changeStatus"),
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
    public EntityModel<FeedbackResponse> setAssignment(@RequestBody SetAssignmentRequest request) {
        SetAssignmentView setAssignmentView = modelMapper.map(request, SetAssignmentView.class);
        FeedbackView assignedFeedback = feedbackService.setAssignment(setAssignmentView);
        FeedbackResponse feedbackResponse = mapViewToResponse(assignedFeedback);
        return EntityModel.of(feedbackResponse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(feedbackResponse.id())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).changeStatus(feedbackResponse.id(), null)).withRel("changeStatus"));
    }

//    @PutMapping("/{id}")
//    public FeedbackView changeStatus(@PathVariable UUID id,  @PathVariable String status){
//        FeedbackView feedbackView = feedbackService.changeStatus(id, status);
//        return feedbackView;
//    }

    @PutMapping("/{id}")
    public EntityModel<FeedbackResponse> changeStatus(@PathVariable UUID id, @RequestParam String status) {
        FeedbackView updatedFeedback = feedbackService.changeStatus(id, status);

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.exchangeName, "feedback.statusChanged",
                feedbackService.getMessageForRabbit(updatedFeedback));

        FeedbackResponse feedbackResponse = mapViewToResponse(updatedFeedback);

        return EntityModel.of(feedbackResponse,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback()).withRel("feedbacks"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeedbackController.class).setAssignment(null)).withRel("setAssignment"));
    }

    AssignmentResponse mapAssignmentViewToResponse(AssignmentView assignmentView){
        return new AssignmentResponse(assignmentView.getId(), assignmentView.getFeedbackId(), assignmentView.getEmployerId());
    }

    FeedbackResponse mapViewToResponse(FeedbackView feedbackView){
        return new FeedbackResponse(
                feedbackView.getMessage(),
                feedbackView.getUserId(),
                feedbackView.getId(),
                feedbackView.getAssignmentId(),
                feedbackView.getStatus()
        );
    }
}
