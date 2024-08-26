package com.example.ecommerce_backend.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;

@Entity(name="permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Permission{
    @Id
    @Column(name="permission_name")
    String name;

    @Column(name="description")
    String description;
}
