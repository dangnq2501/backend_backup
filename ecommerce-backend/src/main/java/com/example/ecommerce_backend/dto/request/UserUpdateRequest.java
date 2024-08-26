package com.example.ecommerce_backend.dto.request;

import com.example.ecommerce_backend.models.Order;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    @Size(min=8, message="Password_invalid")
    String password;
    String email;
    String phone;
    Set<String> roles;
}
