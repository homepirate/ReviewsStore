package com.example.CarSale.Repositories;

import com.example.CarSale.Models.Brand;
import com.example.CarSale.Models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    @Query("SELECT b.models FROM Brand b WHERE b.name = :name")
    List<Model> findModelsByBrandName(@Param("name") String name);

}
