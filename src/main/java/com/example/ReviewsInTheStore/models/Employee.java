package com.example.ReviewsInTheStore.models;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;


@Entity
@Table(name = "employees")
public class Employee extends Base{

    private String name;
    private String role;
    private List<Assignment> assignments;

    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignedTo")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
}
