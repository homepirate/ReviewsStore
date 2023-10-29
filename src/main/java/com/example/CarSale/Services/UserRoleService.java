package com.example.CarSale.Services;

import com.example.CarSale.Services.Dtos.UserRoleDto;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    List<UserRoleDto> getAll();

    UserRoleDto createUserRole(String role);

    void deleteUserRole(UUID userRoleId);
}
