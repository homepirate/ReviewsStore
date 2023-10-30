package com.example.CarSale.Repositories;

import com.example.CarSale.Models.UserRole;
import com.example.CarSale.constants.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    UserRole findByRole(Role role);
}
