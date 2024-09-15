package com.example.ReviewsInTheStore.controllers;

import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackDTO;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

//    @PostMapping
//    public EntityModel<FeedbackDTO> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
//        FeedbackDTO createdFeedback = feedbackService.createFeedback(feedbackDTO);
//        return toModel(createdFeedback);
//    }

//    @GetMapping("/{id}")
//    public EntityModel<FeedbackDTO> getFeedbackById(@PathVariable UUID id) {
//        FeedbackDTO feedbackDTO = feedbackService.findById(id);
//        return toModel(feedbackDTO);
//    }

    @DeleteMapping("/{id}")
    public Class<?> deleteFeedback(@PathVariable UUID id) {
        feedbackService.deleteFeedback(id);
        return null;
    }

    @GetMapping
    public CollectionModel<EntityModel<FeedbackView>> getAllFeedback() {
        List<EntityModel<FeedbackView>> feedbackList = feedbackService.findAll().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(feedbackList);
    }

    private EntityModel<FeedbackView> toModel(FeedbackView feedbackView) {
        EntityModel<FeedbackView> resource = EntityModel.of(feedbackView);

//        Link selfLink = WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(FeedbackController.class).getFeedbackById(feedbackDTO.getId()))
//                .withSelfRel();
//        resource.add(selfLink);

//        Link deleteLink = WebMvcLinkBuilder.linkTo(
//                WebMvcLinkBuilder.methodOn(FeedbackController.class).deleteFeedback(feedbackDTO.getId()))
//                .withRel("delete");
//        resource.add(deleteLink);

        Link allFeedbackLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(FeedbackController.class).getAllFeedback())
                .withRel("allFeedback");
        resource.add(allFeedbackLink);

        return resource;
    }
}
