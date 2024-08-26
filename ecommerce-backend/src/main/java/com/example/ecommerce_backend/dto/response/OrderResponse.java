package com.example.ecommerce_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class OrderResponse {
    UUID id;
    String username;
    boolean paid;
    String status;
    String address;
    double total_cost;
    double shipping_cost;
    Date create_at;
}
