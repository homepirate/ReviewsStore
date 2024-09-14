package com.example.ReviewsInTheStore.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback extends Base {

    private String message;
    private LocalDateTime createdDate;

    @ManyToOne
    private User submittedBy;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private Assignment assignment;

}
