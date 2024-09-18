package com.example.ReviewsInTheStore;

import com.example.ReviewsInTheStore.models.*;
import com.example.ReviewsInTheStore.repositories.AssignmentRepository;
import com.example.ReviewsInTheStore.repositories.EmployeeRepository;
import com.example.ReviewsInTheStore.repositories.FeedbackRepository;
import com.example.ReviewsInTheStore.repositories.UserRepository;
import com.example.ReviewsInTheStore.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private FeedbackRepository feedbackRepository;

    private EmployeeRepository employeeRepository;

    private AssignmentRepository assignmentRepository;

    private UserRepository userRepository;

    @Autowired
    public DataInitializer(FeedbackRepository feedbackRepository, EmployeeRepository employeeRepository, AssignmentRepository assignmentRepository, UserRepository userRepository, EmployeeService employeeService) {
        this.feedbackRepository = feedbackRepository;
        this.employeeRepository = employeeRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setName("Alice");
        user1.setEmail("alice@example.com");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Bob");
        user2.setEmail("bob@example.com");
        userRepository.save(user2);

        // Creating Employees
        Employee employee1 = new Employee();
        employee1.setName("Charlie");
        employee1.setRole(Role.REVIEWER);
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Diana");
        employee2.setRole(Role.MANAGER);
        employeeRepository.save(employee2);

        // Creating Feedback
        Feedback feedback1 = new Feedback();
        feedback1.setMessage("Great job on the project!");
        feedback1.setSubmittedBy(user1);
        feedback1.setStatus(Status.PENDING);
        feedbackRepository.save(feedback1);

        Feedback feedback2 = new Feedback();
        feedback2.setMessage("Needs improvement.");
        feedback2.setSubmittedBy(user2);
        feedback2.setStatus(Status.RESOLVED);
        feedbackRepository.save(feedback2);

        // Creating Assignments
        Assignment assignment1 = new Assignment();
        assignment1.setFeedback(feedback1);
        assignment1.setAssignedTo(employee1);
        assignmentRepository.save(assignment1);

        feedback1.setAssignment(assignment1);
        feedbackRepository.save(feedback1);

        Assignment assignment2 = new Assignment();
        assignment2.setFeedback(feedback2);
        assignment2.setAssignedTo(employee2);
        assignmentRepository.save(assignment2);

        feedback2.setAssignment(assignment2);
        feedbackRepository.save(feedback2);

        // Linking assignments with employees
        employee1.setAssignments(Arrays.asList(assignment1));
        employee2.setAssignments(Arrays.asList(assignment2));
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

    }
}
