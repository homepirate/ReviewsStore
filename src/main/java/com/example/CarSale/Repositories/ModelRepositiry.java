package com.example.CarSale.Repositories;

import com.example.CarSale.Models.Enums.Category;
import com.example.CarSale.Models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelRepositiry extends JpaRepository<Model, UUID> {
    List<Model> findByCategory(Category category);
}
