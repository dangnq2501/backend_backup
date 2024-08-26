package com.example.ecommerce_backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class RoleRequest {
    String role;
    String description;
    Set<String> permissions;
}
