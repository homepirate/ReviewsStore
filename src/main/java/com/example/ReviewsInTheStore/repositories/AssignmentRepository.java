package com.example.ReviewsInTheStore.repositories;

import com.example.ReviewsInTheStore.models.Assignment;
import com.example.ReviewsInTheStore.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    @Override
    Optional<Assignment> findById(UUID uuid);
}
