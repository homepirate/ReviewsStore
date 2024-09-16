package com.example.ReviewsInTheStore.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment extends Base{


    private Feedback feedback;
    private Employee assignedTo;

    @OneToOne
    @JoinColumn(name="feedback_id", referencedColumnName = "id", nullable = false)
    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    @ManyToOne
    @JoinColumn(name="employee_id", referencedColumnName = "id", nullable = false)
    public Employee getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }
}