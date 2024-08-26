package com.example.ecommerce_backend.mapper;

import com.example.ecommerce_backend.dto.request.OrderRequest;
import com.example.ecommerce_backend.dto.response.OrderResponse;
import com.example.ecommerce_backend.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import javax.swing.*;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    Order toOrder(OrderRequest orderRequest);
    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "create_at", source = "order.create_at")
    OrderResponse toOrderResponse(Order order);
}
