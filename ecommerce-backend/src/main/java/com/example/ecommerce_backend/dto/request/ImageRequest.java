package com.example.ecommerce_backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ImageRequest {
    UUID id;
    String pathImage;
    String pathId;
}
