package com.example.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity(name="cart_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem{
    @Id
    @Column(name="cart_item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name="price")
    double price;

    @Column(name="color")
    String color;

    @Column(name="size")
    String size;

    @Column(name="quantity")
    int quantity;

    @Column(name="product_id")
    UUID product_id;

    @Column(name="rating")
    double rating;

}
