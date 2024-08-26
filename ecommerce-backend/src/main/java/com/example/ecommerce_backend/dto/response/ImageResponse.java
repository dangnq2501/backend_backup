package com.example.ecommerce_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ImageResponse {
    UUID id;
    String pathId;
    String pathImage;
}
