package com.example.CarSale.Models;

import com.example.CarSale.Models.Base.BaseEntityCMU;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntityCMU {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private Boolean isActive;
    private UserRole role;

    private Set<Offer> offers;

    public User(String username, String password, String firstName, String lastName, String imageUrl, Boolean isActive, UserRole role) {
        this.username = username;
        this.password = password;
        this.setImageUrl(imageUrl);
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.role = role;
    }

    public User() {
    }

    @Column(name="username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="password", nullable = false)

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="first_name", nullable = false)

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="last_name", nullable = false)

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "is_active", nullable = false)

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }


    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                ", imageUrl='" + imageUrl + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", id=" + id +
                "} " + super.toString();
    }
}
