package com.example.CarSale.Services;

import com.example.CarSale.Dtos.UserRoleDto;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    List<UserRoleDto> getAll();

    UserRoleDto createUserRole(UserRoleDto userRoleDto);

    void deleteUserRole(UUID userRoleId);
}
