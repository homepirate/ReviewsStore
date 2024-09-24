package com.example.ReviewsInTheStore.services.dtos;

import org.springframework.hateoas.EntityModel;

import java.util.Map;

public class UserViewResource extends EntityModel<UserView> {
    private final Map<String, Object> _actions;

    public UserViewResource(UserView userView, Map<String, Object> actions) {
        super(userView);
        this._actions = actions;
    }

    public Map<String, Object> get_actions() {
        return _actions;
    }
}