package com.example.CarSale.Repositories;

import com.example.CarSale.constants.Enums.Category;
import com.example.CarSale.Models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelRepositiry extends JpaRepository<Model, UUID> {
    List<Model> findByCategory(Category category);

    Model findByName(String name);

    @Query("SELECT m FROM Model m JOIN m.offers o GROUP BY m.id ORDER BY COUNT(o) DESC LIMIT 3")
    List<Model> findTopPopularModels();
}
