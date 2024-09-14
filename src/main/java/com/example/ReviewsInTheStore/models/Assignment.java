package com.example.ReviewsInTheStore.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment extends Base{

    @OneToOne
    private Feedback feedback;

    @ManyToOne
    private Employee assignedTo;

    private LocalDate assignedDate;

}