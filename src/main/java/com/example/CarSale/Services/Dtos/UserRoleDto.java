package com.example.CarSale.Services.Dtos;

import com.example.CarSale.constants.Enums.Role;

import java.util.UUID;

public class UserRoleDto {
    private UUID id;
    private Role role;

    public UserRoleDto(UUID id, Role role) {
        this.id = id;
        this.role = role;
    }

    public UserRoleDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoleDto{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
