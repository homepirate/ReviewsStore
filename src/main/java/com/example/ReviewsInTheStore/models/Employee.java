package com.example.ReviewsInTheStore.models;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "employees")
public class Employee extends Base{

    private String name;
    private String role;

    @OneToMany(mappedBy = "assignedTo")
    private List<Assignment> assignments;

    // Getters and Setters
}
