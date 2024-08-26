package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.CartItemRequest;
import com.example.ecommerce_backend.dto.response.CartResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.exception.ErrorCode;
import com.example.ecommerce_backend.mapper.CartItemMapper;
import com.example.ecommerce_backend.mapper.CartMapper;
import com.example.ecommerce_backend.mapper.ProductMapper;
import com.example.ecommerce_backend.models.Cart;
import com.example.ecommerce_backend.models.CartItem;
import com.example.ecommerce_backend.models.Product;
import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repository.CartItemRepository;
import com.example.ecommerce_backend.repository.CartRepository;
import com.example.ecommerce_backend.repository.ProductRepository;
import com.example.ecommerce_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartMapper cartMapper;
    CartItemMapper cartItemMapper;
    ProductMapper productMapper;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;

    ProductRepository productRepository;
    UserRepository userRepository;

    public CartResponse getCart(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        System.out.println(user.getUsername());
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
//        System.out.print("Cart ID: ");
        System.out.println(cart.getCart_id());
        CartResponse cartResponse = cartMapper.toCartResponse(cart);
//        System.out.print("Cart ID: ");
        System.out.println(cartResponse.getCart_id());
        return cartResponse;
    }
    public CartResponse addCartItem(UUID userId, CartItemRequest cartItemRequest){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        CartItem cartItem = cartItemMapper.toCartItem(cartItemRequest);
        List<CartItem> cartItems = cart.getCartItems();
        cartItem.setPrice(cartItem.getPrice()*cartItem.getQuantity());

        cartItemRepository.save(cartItem);
        cartItems.add(cartItem);
        cart.setTotal_price(cart.getTotal_price()+cartItem.getPrice());
        cart.setCartItems(cartItems);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    public CartResponse increaseCartItem(UUID userId, CartItemRequest cartItemRequest){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        CartItem addCartItem = cartItemMapper.toCartItem(cartItemRequest);
        Product product = productRepository.findById(addCartItem.getProduct_id()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        List<CartItem> cartItems = cart.getCartItems();
        for(int i = 0; i < cartItems.size(); i ++){
            CartItem curItem = cartItems.get(i);
            if(curItem.getProduct_id().equals(addCartItem.getId()) && curItem.getProduct_id().equals(addCartItem.getId()) && curItem.getColor().equals(addCartItem.getColor()) ){
                curItem.setQuantity(curItem.getQuantity()+1);
                curItem.setPrice(curItem.getPrice()+product.getPrice());
                cartItemRepository.save(curItem);
                cart.setTotal_price(cart.getTotal_price()+product.getPrice());
                cart.setCartItems(cartItems);

                return cartMapper.toCartResponse(cartRepository.save(cart));
            }
        }
//        System.out.println("Finding sucessfully");
//        CartItem newCartItem = new CartItem();
//        System.out.println(cart.getCart_id());
        addCartItem.setPrice(product.getPrice()*addCartItem.getQuantity());
//        System.out.println("Set sucessfully");
        cartItemRepository.save(addCartItem);
//        System.out.println("Save sucessfully");
        cartItems.add(addCartItem);
        cart.setTotal_price(cart.getTotal_price()+product.getPrice());
        cart.setCartItems(cartItems);

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    public CartResponse deleteCartItem(UUID userId, CartItemRequest cartItemRequest){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        CartItem addCartItem = cartItemMapper.toCartItem(cartItemRequest);
        Product product = productRepository.findById(addCartItem.getProduct_id()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        List<CartItem> cartItems = cart.getCartItems();
        for(int i = 0; i < cartItems.size(); i ++){
            CartItem curItem = cartItems.get(i);
            if(curItem.getProduct_id().equals(addCartItem.getId()) && curItem.getProduct_id().equals(addCartItem.getId()) && curItem.getColor().equals(addCartItem.getColor()) ){
                cart.setTotal_price(cart.getTotal_price()-curItem.getPrice());
                cartItems.remove(i);
                cart.setCartItems(cartItems);
                return cartMapper.toCartResponse(cartRepository.save(cart));
            }
        }
        cart.setCartItems(cartItems);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    public CartResponse decreaseCartItem(UUID userId, CartItemRequest cartItemRequest){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Cart cart = cartRepository.findById(user.getCart_id()).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        CartItem addCartItem = cartItemMapper.toCartItem(cartItemRequest);
        Product product = productRepository.findById(addCartItem.getProduct_id()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        List<CartItem> cartItems = cart.getCartItems();
        for(int i = 0; i < cartItems.size(); i ++){
            CartItem curItem = cartItems.get(i);
            if(curItem.getProduct_id().equals(addCartItem.getId()) && curItem.getProduct_id().equals(addCartItem.getId()) && curItem.getColor().equals(addCartItem.getColor()) ){

                if(curItem.getQuantity() == 1){
                    cartItems.remove(i);
                    break;
                }
                curItem.setQuantity(curItem.getQuantity()-1);
                curItem.setPrice(curItem.getPrice()-product.getPrice());
                cart.setTotal_price(cart.getTotal_price()-product.getPrice());
                cart.setCartItems(cartItems);
                return cartMapper.toCartResponse(cartRepository.save(cart));
            }
        }
        cart.setTotal_price(cart.getTotal_price()-product.getPrice());
        cart.setCartItems(cartItems);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    public CartResponse deleteAllCartItem(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.clear();
        cart.setCartItems(cartItems);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }
}
