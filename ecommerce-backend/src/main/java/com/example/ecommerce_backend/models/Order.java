package com.example.ecommerce_backend.models;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Entity(name="\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Order{
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name="address")
    String address;

    @Column(name="create_at")
    Date create_at;

    @Column(name="paid")
    boolean paid;

    @Column(name="status")
    String status;

    @Column(name="cart_id")
    UUID cart_id;

    @Column(name="username")
    String username;

    @Column(name="shipping_cost")
    double shipping_cost;

    @Column(name="total_cost")
    double total_cost;

}

