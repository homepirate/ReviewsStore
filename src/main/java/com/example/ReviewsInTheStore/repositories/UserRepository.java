package com.example.ReviewsInTheStore.repositories;

import com.example.ReviewsInTheStore.models.Feedback;
import com.example.ReviewsInTheStore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User>  findByEmail(String email);

}
