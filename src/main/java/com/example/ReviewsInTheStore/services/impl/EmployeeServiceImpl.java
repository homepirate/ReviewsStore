package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.Assignment;
import com.example.ReviewsInTheStore.models.Employee;
import com.example.ReviewsInTheStore.models.Role;
import com.example.ReviewsInTheStore.repositories.EmployeeRepository;
import com.example.ReviewsInTheStore.services.EmployeeService;
import com.example.ReviewsInTheStore.services.dtos.AssignmentView;
import com.example.ReviewsInTheStore.services.dtos.EmployeeView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public EmployeeView createEmployee(EmployeeView employeeView) {
        Employee employee = modelMapper.map(employeeView, Employee.class);
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        return modelMapper.map(savedEmployee, EmployeeView.class);
    }

    @Override
    public String deleteEmployee(UUID employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()){
            return null;
        }
        employeeRepository.deleteById(employeeId);
        return "Employee deleted";
    }

    @Override
    public List<EmployeeView> findAll() {

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeView> employeeViews = new ArrayList<>();
        for (Employee employee : employees){
            EmployeeView employeeView = modelMapper.map(employee, EmployeeView.class);
            employeeView.setAssignmentViewList(getEmployeeAssignments(employee));
            employeeViews.add(employeeView);
        }
        return employeeViews;
    }

    @Override
    public EmployeeView findById(UUID id){
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isEmpty()){
            return null;
        }
        Employee employee = employeeOpt.get();
        EmployeeView employeeView = modelMapper.map(employee, EmployeeView.class);
        employeeView.setAssignmentViewList(getEmployeeAssignments(employee));
        return employeeView;
    }


    public EmployeeView changeRole(UUID id, String newRole) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return null;
        }
        Employee employee = optionalEmployee.get();
        try {
                employee.setRole(Role.valueOf(newRole));
            }
        catch (IllegalArgumentException e) {
            return null;
        }

        EmployeeView employeeView = modelMapper.map(employeeRepository.saveAndFlush(employee), EmployeeView.class);
        employeeView.setAssignmentViewList(getEmployeeAssignments(employee));
        return employeeView;
    }

    public List<AssignmentView> getEmployeeAssignments(Employee employee){
        List<AssignmentView> assignmentViews = new ArrayList<>();
        List<Assignment> assignments = employee.getAssignments();
        for (Assignment assignment: assignments){
            assignmentViews.add(new AssignmentView(assignment.getId(),
                    assignment.getFeedback().getId(), assignment.getAssignedTo().getId()));
        }
        return assignmentViews;
    }
}
