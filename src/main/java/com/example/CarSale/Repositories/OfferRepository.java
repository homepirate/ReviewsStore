package com.example.CarSale.Repositories;

import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.constants.Enums.Engine;
import com.example.CarSale.constants.Enums.Transmission;
import com.example.CarSale.Models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {


    List<Offer> findByEngine(Engine engine);

    List<Offer> findByTransmission(Transmission transmission);


    @Query("SELECT o FROM Offer o ")
    List<Offer> getAllOffersWithInfo();

    @Query("SELECT o FROM Offer o WHERE o.id = :offerId")
    Offer getALLInfoOneOffer(@Param("offerId") UUID offerId);


    @Query("SELECT o FROM Offer o " +
            "WHERE (o.engine IN :engineFilter) AND " +
            "(o.transmission IN :transmissionFilter)"  +
            "AND (:modelFilter IS NULL OR o.model.name = :modelFilter)"
            )
    List<Offer> getFilteredOffers(@Param("engineFilter") List<Engine> engineFilter,
                                  @Param("transmissionFilter") List<Transmission> transmissionFilter,
                                                  @Param("modelFilter") String model);


    @Query("SELECT new map(o.created as created, o.modified as modified) FROM Offer o WHERE o.id = :offerId")
    Map<String, LocalDateTime> findOfferDetailsById(@Param("offerId") UUID offerId);

}
