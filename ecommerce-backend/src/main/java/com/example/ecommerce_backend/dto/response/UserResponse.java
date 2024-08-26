package com.example.ecommerce_backend.dto.response;

import com.example.ecommerce_backend.models.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String username;
    String password;
    String email;
    String phone;
    UUID cart_id;
    List<Order> orders;
}
