package com.example.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Entity(name="image")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name="path_id")
    String pathId;

    @Column(name="path_image")
    String pathImage;
}
