package com.example.CarSale.Models;

import com.example.CarSale.Models.Base.BaseEntityCMU;
import com.example.CarSale.Models.Enums.Engine;
import com.example.CarSale.Models.Enums.Transmission;
import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntityCMU {

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="engine", nullable = false)
    private Engine engine;

    @Column(name="mileage", nullable = false)
    private int mileage;

    @Column(name="price", nullable = false)
    private int price;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transmission", nullable = false)
    private Transmission transmission;

    @Column(name="year", nullable = false)
    private int year;

    @ManyToOne(optional = false)
    @JoinColumn(name="seller_id", referencedColumnName = "id", nullable = false)
    private User seller;


    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    private Model model;

    public Offer(String description, Engine engine, int mileage, int price, String url, Transmission transmission, int year, User seller, Model model) {
        this.description = description;
        this.engine = engine;
        this.mileage = mileage;
        this.price = price;
        this.setImageUrl(url);
        this.transmission = transmission;
        this.year = year;
        this.seller = seller;
        this.model = model;
    }

    public Offer() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "description='" + description + '\'' +
                ", engine=" + engine +
                ", mileage=" + mileage +
                ", price=" + price +
                ", transmission=" + transmission +
                ", year=" + year +
                ", seller=" + seller +
                ", model=" + model +
                ", created=" + created +
                ", modified=" + modified +
                ", id=" + id +
                "} " + super.toString();
    }
}
