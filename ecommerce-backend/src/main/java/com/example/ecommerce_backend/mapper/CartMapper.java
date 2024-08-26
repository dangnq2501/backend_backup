package com.example.ecommerce_backend.mapper;

import com.example.ecommerce_backend.dto.request.CartRequest;
import com.example.ecommerce_backend.dto.response.CartResponse;
import com.example.ecommerce_backend.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper {

    Cart toCart(CartRequest cartRequest);
    @Mapping(target = "cart_id", source = "cart.cart_id")
    CartResponse toCartResponse(Cart cart);
    void updateCart(@MappingTarget Cart cart, CartRequest cartRequest);
}
