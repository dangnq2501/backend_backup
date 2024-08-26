package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.CartItemRequest;
import com.example.ecommerce_backend.dto.response.ApiResponse;
import com.example.ecommerce_backend.dto.response.CartResponse;
import com.example.ecommerce_backend.service.CartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @PostMapping("/user/{userId}/cart/increase/{productId}")
    ApiResponse<CartResponse> increaseCartItem(@PathVariable("userId") UUID userId,
                                          @RequestBody @Valid CartItemRequest cartItemRequest){
        CartResponse cartResponse = cartService.increaseCartItem(userId, cartItemRequest);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }

    @PostMapping("/user/{userId}/cart/add/{productId}")
    ApiResponse<CartResponse> addCartItem(@PathVariable("userId") UUID userId,
                                          @RequestBody @Valid CartItemRequest cartItemRequest){
        CartResponse cartResponse = cartService.addCartItem(userId, cartItemRequest);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }
    @DeleteMapping("/user/{userId}/cart/delete/{productId}")
    ApiResponse<CartResponse> deleteCartItem(@PathVariable("userId") UUID userId,
                                             @RequestBody @Valid CartItemRequest cartItemRequest){
        CartResponse cartResponse = cartService.deleteCartItem(userId, cartItemRequest);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }

    @DeleteMapping("/user/{userId}/cart/decrease/{productId}")
    ApiResponse<CartResponse> decreaseCartItem(@PathVariable("userId") UUID userId,
                                               @RequestBody @Valid CartItemRequest cartItemRequest){
        CartResponse cartResponse = cartService.decreaseCartItem(userId, cartItemRequest);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }
    @GetMapping("/user/{userId}/cart")
    ApiResponse<CartResponse> getCart(@PathVariable("userId") UUID userId){
        CartResponse cartResponse = cartService.getCart(userId);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }

    @DeleteMapping("/user/{userId}/cart/deleteAll")
    ApiResponse<CartResponse> deleteAllCartItem(@PathVariable("userId") UUID userId){
        CartResponse cartResponse = cartService.deleteAllCartItem(userId);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }


}
