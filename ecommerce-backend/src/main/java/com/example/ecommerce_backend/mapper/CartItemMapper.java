package com.example.ecommerce_backend.mapper;

import com.example.ecommerce_backend.dto.request.CartItemRequest;
import com.example.ecommerce_backend.dto.response.CartItemResponse;
import com.example.ecommerce_backend.dto.response.CartResponse;
import com.example.ecommerce_backend.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartItemMapper {
    CartItem toCartItem(CartItemRequest cartItemRequest);
    @Mapping(target = "id", source = "cartItem.id")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
