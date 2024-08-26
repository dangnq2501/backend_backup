package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.OrderRequest;
import com.example.ecommerce_backend.dto.response.OrderResponse;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.exception.ErrorCode;
import com.example.ecommerce_backend.mapper.CartMapper;
import com.example.ecommerce_backend.mapper.OrderMapper;
import com.example.ecommerce_backend.models.*;
import com.example.ecommerce_backend.repository.CartRepository;
import com.example.ecommerce_backend.repository.OrderRepository;
import com.example.ecommerce_backend.repository.ProductRepository;
import com.example.ecommerce_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderMapper orderMapper;
    OrderRepository orderRepository;
    UserRepository userRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;
    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse getOrder(UUID orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        return orderMapper.toOrderResponse(order);
    }
    @PostAuthorize("returnObject.username == authentication.name")
    public OrderResponse myOrder(UUID orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        return orderMapper.toOrderResponse(order);
    }


    @PostAuthorize("returnObject.username == authentication.name")
    public OrderResponse createOrder(UUID userID, OrderRequest orderRequest){
        Order order = orderMapper.toOrder(orderRequest);
//        System.out.println("order: " + order.getCart_id());
        User user = userRepository.findById(userID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Cart curCart = cartRepository.findById(user.getCart_id()).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        List<CartItem> cartItemList = curCart.getCartItems();
        for(int i = 0; i < cartItemList.size(); i ++){
            CartItem cartItem = cartItemList.get(i);
            Product product = productRepository.findById(cartItem.getProduct_id()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
            if(cartItem.getQuantity() > product.getStockQuantity()){
                product.setStockQuantity(product.getStockQuantity()-cartItem.getQuantity());
                productRepository.save(product);
            }
            else{
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);
            }
        }
        List<Order> orders = user.getOrders();
        Date date = new Date();
//        System.out.println(date);
        order.setCreate_at(date);
        order.setCart_id(user.getCart_id());
        order.setUsername(user.getUsername());
        order.setTotal_cost(order.getShipping_cost()+curCart.getTotal_price());
        orderRepository.save(order);
//        System.out.println("order: " + order.getCreate_at());

        orders.add(order);
        user.setOrders(orders);
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        cart.setCartItems(cartItems);
        cart.setTotal_price(0);
        cartRepository.save(cart);
        user.setCart_id(cart.getCart_id());
        userRepository.save(user);
//        System.out.println("order: " + order.getCart_id());
        return orderMapper.toOrderResponse(order);
    }

}
