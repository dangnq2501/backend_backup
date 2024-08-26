package com.example.ecommerce_backend.repository;

import com.example.ecommerce_backend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
