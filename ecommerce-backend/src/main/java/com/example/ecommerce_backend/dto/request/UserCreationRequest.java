package com.example.ecommerce_backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min=4, message="USERNAME_INVALID")
    String username;

    @Size(min=8, message="PASSWORD_INVALID")
    String password;

    String email;
    String phone;
    Set<String> roles;
}
