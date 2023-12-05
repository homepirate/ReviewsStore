package com.example.CarSale.Repositories;

import com.example.CarSale.Views.AllOfferWithBrandView;
import com.example.CarSale.constants.Enums.Role;
import com.example.CarSale.Models.Offer;
import com.example.CarSale.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByRoleRole(Role role);
    @Query("SELECT o FROM User u JOIN u.offers o WHERE u.id = :userId")
    List<Offer> getOffersByUserId(@Param("userId") UUID userId);


    @Query("SELECT new com.example.CarSale.Views.AllOfferWithBrandView(b.name, m.name, o.price, o.imageUrl, o.engine, o.mileage, o.year, o.transmission, u.firstName, u.lastName, u.username) FROM Offer o  " +
            "JOIN o.model m " +
            "JOIN m.brand b " +
            "JOIN o.seller u WHERE u.username = :username")
    List<AllOfferWithBrandView> getAllUserOffers(@Param("username") String username);

//    User findUserByUsername(String username);
    Optional<User> findByUsername(String username);
}
