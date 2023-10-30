package com.example.CarSale.Services;

import com.example.CarSale.Services.Dtos.UserDto;
import com.example.CarSale.Services.Dtos.UserRoleDto;
import com.example.CarSale.constants.Enums.Role;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    List<UserRoleDto> getAll();

    UserRoleDto createUserRole(String role);

    void deleteUserRole(UUID userRoleId);

    UserRoleDto getByRole(Role role);
}
