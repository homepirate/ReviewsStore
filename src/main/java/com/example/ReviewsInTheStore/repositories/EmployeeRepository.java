package com.example.ReviewsInTheStore.repositories;

import com.example.ReviewsInTheStore.models.Employee;
import com.example.ReviewsInTheStore.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
