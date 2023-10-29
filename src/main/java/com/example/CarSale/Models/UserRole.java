package com.example.CarSale.Models;

import com.example.CarSale.Models.Base.BaseID;
import com.example.CarSale.constants.Enums.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
public class UserRole extends BaseID {

    private Role role;
    private Set<User> users;

    public UserRole(Role role) {
        this.role = role;
    }

    public UserRole() {
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "name", nullable = false)
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
