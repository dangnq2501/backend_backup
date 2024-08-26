package com.example.ecommerce_backend.repository;

import com.example.ecommerce_backend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    public Image findByPathImage(String path);
}
