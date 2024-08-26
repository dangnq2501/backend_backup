package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.OrderRequest;
import com.example.ecommerce_backend.dto.response.ApiResponse;
import com.example.ecommerce_backend.dto.response.OrderResponse;
import com.example.ecommerce_backend.service.OrderService;
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
public class OrderController {
    OrderService orderService;

    @GetMapping("order/{orderId}")
    ApiResponse<OrderResponse> getOrder(@PathVariable("orderId") UUID orderId){
        OrderResponse orderResponse = orderService.getOrder(orderId);
        ApiResponse<OrderResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderResponse);
        return apiResponse;
    }
    @GetMapping("user/{userId}/order/{orderId}")
    ApiResponse<OrderResponse> myOrder(@PathVariable("userId") UUID userId,
                                        @PathVariable("orderId") UUID orderId){
        OrderResponse orderResponse = orderService.myOrder(orderId);
        ApiResponse<OrderResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderResponse);
        return apiResponse;
    }
    @PostMapping("user/{userId}/order")
    ApiResponse<OrderResponse> createOrder(@PathVariable("userId") UUID userId, @RequestBody @Valid OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.createOrder(userId, orderRequest);
        ApiResponse<OrderResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderResponse);
        return apiResponse;
    }

}
