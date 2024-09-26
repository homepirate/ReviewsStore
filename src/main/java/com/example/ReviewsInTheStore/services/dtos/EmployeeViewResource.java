package com.example.ReviewsInTheStore.services.dtos;

import org.springframework.hateoas.EntityModel;

import java.util.Map;

public class EmployeeViewResource extends EntityModel<EmployeeView> {
    private final Map<String, Object> _actions;

    public EmployeeViewResource(EmployeeView employeeView, Map<String, Object> actions) {
        super(employeeView);
        this._actions = actions;
    }

    public Map<String, Object> get_actions() {
        return _actions;
    }
}
