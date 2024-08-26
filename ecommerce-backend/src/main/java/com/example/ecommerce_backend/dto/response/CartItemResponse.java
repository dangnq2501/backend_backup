package com.example.ecommerce_backend.dto.response;

import com.example.ecommerce_backend.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CartItemResponse {
    UUID id;
    UUID product_id;
    int quantity;
    double price;
    double rating;
    String size;
    String color;
}
