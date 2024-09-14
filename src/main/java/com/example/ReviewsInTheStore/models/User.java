package com.example.ReviewsInTheStore.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends Base{

    private String name;
    private String email;

}