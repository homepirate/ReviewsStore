package com.example.ReviewsInTheStore.services.impl;

import com.example.ReviewsInTheStore.models.Assignment;
import com.example.ReviewsInTheStore.models.Employee;
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

//    @Override
//    public EmployeeDTO createEmployee(EmployeeDTO employeeDto) {
//        Employee employee = modelMapper.map(employeeDto, Employee.class);
//        Employee savedEmployee = employeeRepository.save(employee);
//        return modelMapper.map(savedEmployee, EmployeeDTO.class);
//    }

    @Override
    public EmployeeView createEmployee(EmployeeView employeeView) {
        Employee employee = modelMapper.map(employeeView, Employee.class);
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        return modelMapper.map(savedEmployee, EmployeeView.class);
    }

    @Override
    public void deleteEmployee(UUID employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public List<EmployeeView> findAll() {

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeView> employeeViews = new ArrayList<>();
        for (Employee employee : employees){
            EmployeeView employeeView = modelMapper.map(employee, EmployeeView.class);
            List<AssignmentView> assignmentViews = new ArrayList<>();
            List<Assignment> assignments = employee.getAssignments();
            for (Assignment assignment: assignments){
                assignmentViews.add(new AssignmentView(assignment.getId(),
                        assignment.getFeedback().getId(), assignment.getAssignedTo().getId()));
            }
            employeeView.setAssignmentViewList(assignmentViews);
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
        List<AssignmentView> assignmentViews = new ArrayList<>();
        List<Assignment> assignments = employee.getAssignments();
        for (Assignment assignment: assignments){
            assignmentViews.add(new AssignmentView(assignment.getId(),
                    assignment.getFeedback().getId(), assignment.getAssignedTo().getId()));
        }
        employeeView.setAssignmentViewList(assignmentViews);
        return employeeView;
    }
}
