package com.example.CarSale.Repositories;

import com.example.CarSale.Models.Enums.Role;
import com.example.CarSale.Models.Offer;
import com.example.CarSale.Models.User;
import com.example.CarSale.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByRoleRole(Role role);
    @Query("SELECT o FROM User u JOIN u.offers o WHERE u.id = :userId")
    List<Offer> getOffersByUserId(@Param("userId") UUID userId);
}
