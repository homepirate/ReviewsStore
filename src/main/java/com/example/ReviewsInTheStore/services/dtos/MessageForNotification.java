package com.example.ReviewsInTheStore.services.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageForNotification {
    private UUID assignmentId;
    private UUID feedbackId;
    private String feedbackMessage;
    private UUID employeeId;
    private String employeeName;
    private String notificationType;
    private LocalDateTime createdAt;


    public MessageForNotification() {
    }

    public UUID getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(UUID assignmentId) {
        this.assignmentId = assignmentId;
    }

    public UUID getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(UUID feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MessageForNotification{" +
                "assignmentId=" + assignmentId +
                ", feedbackId=" + feedbackId +
                ", feedbackMessage='" + feedbackMessage + '\'' +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
