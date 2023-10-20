package com.example.CarSale.Repositories;

import com.example.CarSale.Dtos.AllOffersWithBrandDto;
import com.example.CarSale.Models.Enums.Engine;
import com.example.CarSale.Models.Enums.Transmission;
import com.example.CarSale.Models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {


    List<Offer> findByEngine(Engine engine);

    List<Offer> findByTransmission(Transmission transmission);


    @Query("SELECT new com.example.CarSale.Dtos.AllOffersWithBrandDto(b.name, m.name, o.price, o.imageUrl, o.engine, o.mileage, o.year, o.transmission, u.firstName, u.lastName) " +
            "FROM Offer o " +
            "JOIN o.model m " +
            "JOIN m.brand b " +
            "JOIN o.seller u")
    List<AllOffersWithBrandDto> getAllOffersWithInfo();
}
