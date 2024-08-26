package com.example.ecommerce_backend.repository;

import com.example.ecommerce_backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByPathId(String PathId);

    List<Product> findAllByPriceBetween(double minPrice, double maxPrice);
    List<Product> findAllByRatingBetween(double minRate, double maxRate);
    List<Product> findAllByName(String name);

    Product findByPathId(String path_id);
}
