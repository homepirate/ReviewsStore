package com.example.CarSale.Models;

import com.example.CarSale.Models.Base.BaseEntityCM;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="brands")
public class Brand extends BaseEntityCM {

    private String name;
    private Set<Model> models;

    public Brand(String name) {
        this.name = name;
    }

    public Brand() {
    }

    @Column(name="name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
