package com.example.ecommerce_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    boolean isSuccess;

}
