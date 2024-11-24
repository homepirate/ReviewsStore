package com.example.ReviewsInTheStore.services.dtos;

import com.example.contract_first.dto.EmployeeResponse;
import org.springframework.hateoas.EntityModel;

import java.util.Map;

public class EmployeeViewResource extends EntityModel<EmployeeResponse> {
    private final Map<String, Object> _actions;

    public EmployeeViewResource(EmployeeResponse employeeResponse, Map<String, Object> actions) {
        super(employeeResponse);
        this._actions = actions;
    }

    public Map<String, Object> get_actions() {
        return _actions;
    }
}
