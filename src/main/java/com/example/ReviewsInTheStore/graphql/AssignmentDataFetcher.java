package com.example.ReviewsInTheStore.graphql;


import com.example.ReviewsInTheStore.services.AssignmentService;
import com.example.ReviewsInTheStore.services.dtos.AssignmentView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class AssignmentDataFetcher {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentDataFetcher(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }


    @DgsQuery
    public List<AssignmentView> allAssignments() {
        List<AssignmentView> assignmentViews = assignmentService.findAll();
        return assignmentViews;

    }

    @DgsQuery
    public AssignmentView assignmentById(@InputArgument String id) {
        UUID assignmentId = UUID.fromString(id);
        return assignmentService.findById(assignmentId);
    }
}
