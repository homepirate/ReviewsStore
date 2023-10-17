package com.example.CarSale.Models;

import com.example.CarSale.Models.Base.BaseEntityCM;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name="brands")
public class Brand extends BaseEntityCM {
    @Column(name="name", nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Model> models;

    public Brand(String name) {
        this.name = name;
    }

    public Brand() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
