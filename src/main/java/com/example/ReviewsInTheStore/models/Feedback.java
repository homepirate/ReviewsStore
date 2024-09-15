package com.example.ReviewsInTheStore.models;


import jakarta.persistence.*;


@Entity
@Table(name = "feedbacks")
public class Feedback extends Base {

    private String message;
    private User submittedBy;
    private Status status;
    private Assignment assignment;


    @Column(name="message", nullable = false)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    public User getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(User submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @OneToOne
    @JoinColumn(name="assignment_id", referencedColumnName = "id")
    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
}
