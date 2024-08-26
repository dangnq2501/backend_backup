package com.example.ecommerce_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ProductResponse {
    UUID id;
    String name;
    String category;
    String description;
    String pathId;
    double price;
    int stockQuantity;
    double rating;
}
