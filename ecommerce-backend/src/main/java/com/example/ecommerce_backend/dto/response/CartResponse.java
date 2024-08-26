package com.example.ecommerce_backend.dto.response;

import com.example.ecommerce_backend.models.CartItem;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CartResponse {
    UUID cart_id;
    double total_price;
    List<CartItem> cartItems;
}
