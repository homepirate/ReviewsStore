package com.example.ReviewsInTheStore.services.dtos;

import com.example.contract_first.dto.UserResponse;
import org.springframework.hateoas.EntityModel;

import java.util.Map;

public class UserViewResource extends EntityModel<UserResponse> {
    private final Map<String, Object> _actions;

    public UserViewResource(UserResponse userResponse, Map<String, Object> actions) {
        super(userResponse);
        this._actions = actions;
    }

    public Map<String, Object> get_actions() {
        return _actions;
    }
}