package com.example.ReviewsInTheStore.repositories;

import com.example.ReviewsInTheStore.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    @Override
    Optional<Assignment> findById(UUID uuid);

    @Modifying
    @Query("DELETE FROM Assignment a WHERE a.feedback.id = :feedbackId")
    void deleteAssignmentByFeedbackId(@Param("feedbackId") UUID feedbackId);
}
