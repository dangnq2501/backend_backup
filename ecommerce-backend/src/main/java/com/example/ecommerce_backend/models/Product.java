package com.example.ecommerce_backend.models;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity(name="product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product{
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name="name")
    String name;

    @Column(name="description")
    String description;

    @Column(name="category")
    String category;

    @Column(name="path_id")
    String pathId;

    @Column(name="price")
    double price;

    @Column(name="stock_quantity")
    int stockQuantity;

    @Column(name="rating")
    double rating;
}
