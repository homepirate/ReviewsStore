package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.*;
import com.example.ReviewsInTheStore.repositories.AssignmentRepository;
import com.example.ReviewsInTheStore.repositories.EmployeeRepository;
import com.example.ReviewsInTheStore.repositories.FeedbackRepository;
import com.example.ReviewsInTheStore.repositories.UserRepository;
import com.example.ReviewsInTheStore.services.FeedbackService;
import com.example.ReviewsInTheStore.services.dtos.FeedbackCreateView;
import com.example.ReviewsInTheStore.services.dtos.FeedbackDTO;
import com.example.ReviewsInTheStore.services.dtos.FeedbackView;
import com.example.ReviewsInTheStore.services.dtos.SetAssignmentView;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private ModelMapper modelMapper;
    private FeedbackRepository feedbackRepository;
    private UserRepository userRepository;
    private EmployeeRepository employeeRepository;
    private AssignmentRepository assignmentRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper, UserRepository userRepository,
                               EmployeeRepository employeeRepository, AssignmentRepository assignmentRepository) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public FeedbackView createFeedback(FeedbackCreateView feedbackCreateView) {
        Optional<User> user = userRepository.findById(feedbackCreateView.getUserId());
        Feedback feedback = modelMapper.map(feedbackCreateView, Feedback.class);
        if (user.isEmpty()) {
            return null;
        }
        feedback.setSubmittedBy(user.get());
        Feedback savedFeedback = feedbackRepository.save(feedback);
        FeedbackView feedbackView = modelMapper.map(savedFeedback, FeedbackView.class);
        feedbackView.setUserId(user.get().getId());
        return feedbackView;
    }

    @Override
    @Transactional
    public void deleteFeedback(UUID feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isEmpty()){
            return;
        }
        Feedback fb = feedback.get();
        fb.setAssignment(null);
        feedbackRepository.saveAndFlush(fb);
        assignmentRepository.deleteAssignmentByFeedbackId(feedbackId);
        feedbackRepository.deleteById(feedbackId);
    }

    @Override
    public List<FeedbackDTO> find() {
        return feedbackRepository.findAll().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackView> findAll(){
        List<FeedbackView> feedbackViews = new ArrayList<>();
        for (FeedbackDTO feedbackDTO : find()) {
            FeedbackView feedbackView = modelMapper.map(feedbackDTO, FeedbackView.class);
            feedbackView.setUserId(feedbackDTO.getSubmittedBy().getId());
            feedbackView.setAssignmentId(feedbackDTO.getAssignment().getId());
            feedbackViews.add(feedbackView);
        }
        return feedbackViews;
    }

    @Override
    public FeedbackView findById(UUID feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isEmpty()){
            return null;
        }
        Feedback feedbackModel = feedback.get();
        FeedbackView feedbackView =  modelMapper.map(feedbackModel, FeedbackView.class);
        feedbackView.setUserId(feedbackModel.getSubmittedBy().getId());
        return feedbackView;
    }

    @Override
    public FeedbackView setAssignment(SetAssignmentView setAssignmentView) {
        Optional<Employee> employee = employeeRepository.findById(setAssignmentView.getEmployeeId());
        Optional<Feedback> feedback = feedbackRepository.findById(setAssignmentView.getFeedbackId());

        if (employee.isEmpty() || feedback.isEmpty()){
            return null;
        }
        Assignment assignment = new Assignment();
        Feedback feedback1 = feedback.get();
        assignment.setFeedback(feedback1);
        assignment.setAssignedTo(employee.get());
        feedback1.setAssignment(assignment);
        assignmentRepository.save(assignment);
        feedbackRepository.save(feedback1);
        FeedbackView feedbackView = modelMapper.map(feedback1, FeedbackView.class);
        feedbackView.setUserId(feedback1.getSubmittedBy().getId());
        return feedbackView;
    }

    @Override
    public FeedbackView changeStatus(UUID id, String status) {
        Optional<Feedback> feedbackOpt = feedbackRepository.findById(id);
        Status newStatus;
        try {
            newStatus = Status.valueOf(status.toUpperCase());
        }
        catch (IllegalArgumentException e){
            return null;
        }
        Feedback feedback = feedbackOpt.get();
        feedback.setStatus(newStatus);
        Feedback feedback1 = feedbackRepository.saveAndFlush(feedback);
        FeedbackView feedbackView = modelMapper.map(feedback1, FeedbackView.class);
        feedbackView.setUserId(feedback1.getSubmittedBy().getId());
        feedbackView.setAssignmentId(feedback.getAssignment().getId());
        return feedbackView;
    }
}
