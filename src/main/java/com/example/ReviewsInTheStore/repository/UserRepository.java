package com.example.ReviewsInTheStore.repository;

import com.example.ReviewsInTheStore.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Feedback, UUID> {
}
