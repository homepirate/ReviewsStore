package com.example.CarSale.Models;

import com.example.CarSale.Models.Base.BaseID;
import com.example.CarSale.Models.Enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "roles")
public class UserRole extends BaseID {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "name", nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<User> users;

    public UserRole(Role role) {
        this.role = role;
    }

    public UserRole() {
    }

    public List<User> getUsers() {
        return users;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role=" + role +
                ", id=" + id +
                "} " + super.toString();
    }
}
