package com.example.ecommerce_backend.dto.request;

import com.example.ecommerce_backend.models.CartItem;
import com.example.ecommerce_backend.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CartRequest {
    UUID cart_id;
    double total_price;
    List<CartItem> cartItems;
}
