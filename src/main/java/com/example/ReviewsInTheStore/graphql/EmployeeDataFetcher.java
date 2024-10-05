package com.example.ReviewsInTheStore.graphql;


import com.example.ReviewsInTheStore.services.EmployeeService;
import com.example.ReviewsInTheStore.services.dtos.EmployeeView;
import com.example.ReviewsInTheStore.services.dtos.UserView;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class EmployeeDataFetcher {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeDataFetcher(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @DgsQuery
    public List<EmployeeView> allEmployees() {
        List<EmployeeView> employeeViews = employeeService.findAll();
        return employeeViews;

    }

    @DgsQuery
    public EmployeeView employeeById(@InputArgument String id) {
        UUID employeeId = UUID.fromString(id);
        return employeeService.findById(employeeId);
    }

    @DgsMutation
    public EmployeeView createEmployee(@InputArgument EmployeeView employeeView) {
        EmployeeView createdEmployee = employeeService.createEmployee(employeeView);
        return createdEmployee;
    }

    @DgsMutation
    public EmployeeView changeRole(@InputArgument String id, @InputArgument String role) {
        UUID employeeId = UUID.fromString(id);
        EmployeeView updatedEmployee = employeeService.changeRole(employeeId, role);
        return updatedEmployee;
    }
}
