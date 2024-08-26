package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.response.CartItemResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.exception.ErrorCode;
import com.example.ecommerce_backend.mapper.CartItemMapper;
import com.example.ecommerce_backend.models.CartItem;
import com.example.ecommerce_backend.repository.CartItemRepository;

import java.util.UUID;

public class CartItemService {
    CartItemMapper cartItemMapper;
    CartItemRepository cartItemRepository;
    public CartItemResponse getById(UUID id){
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_EXISTED));
        return cartItemMapper.toCartItemResponse(cartItem);
    }
}
