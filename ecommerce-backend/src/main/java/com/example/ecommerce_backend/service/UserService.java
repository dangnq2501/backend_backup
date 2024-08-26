package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.UserCreationRequest;
import com.example.ecommerce_backend.dto.request.UserUpdateRequest;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.dto.response.UserResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.exception.ErrorCode;
import com.example.ecommerce_backend.mapper.ProductMapper;
import com.example.ecommerce_backend.mapper.UserMapper;
import com.example.ecommerce_backend.models.*;
import com.example.ecommerce_backend.repository.CartRepository;
import com.example.ecommerce_backend.repository.ProductRepository;
import com.example.ecommerce_backend.repository.RoleRepository;
import com.example.ecommerce_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    CartRepository cartRepository;
    RecommendationService recommendationService;
    ProductRepository productRepository;
    ProductMapper productMapper;
    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        List<Role> roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));
        user.setPassword((user.getPassword()));

        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        user.setOrders(orders);
        cart.setCartItems(cartItems);
        cart.setTotal_price(0);
        cartRepository.save(cart);
//        for(Role r: roles){
//            System.out.println("Role: " + r.getName());
//        }

        user.setCart_id(cart.getCart_id());
//        System.out.print("Cart ID: ");
//        System.out.println(user.getCart_id());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(UUID userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return updateUser(request, user);
    }
    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return updateUser(request, user);
    }

    private UserResponse updateUser(UserUpdateRequest request, User user) {
        if (request.getRoles() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.deleteById(id);
    }

    public List<ProductResponse> recommend(UUID user_id){
//        System.out.println("In process");
        User user = userRepository.findById(user_id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Order> orders = user.getOrders();
        List<String> product_history = new ArrayList<>();
        List<Float> rating_history = new ArrayList<>();
        product_history.add("denim_jackets_91181826");
        rating_history.add((float)3.0);
        for (Order order : orders) {
            Cart cart = cartRepository.findById(order.getCart_id()).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
            List<CartItem> cartItems = cart.getCartItems();
            for (CartItem cartItem : cartItems) {
                Product product = productRepository.findById(cartItem.getProduct_id()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
                product_history.add(product.getPathId());
                double rate = cartItem.getRating();
                float frate =(float) rate;

                rating_history.add(frate);
            }
        }
        List<String> recommends = recommendationService.getRecommendations(product_history, rating_history, user.getPhone());
        List<ProductResponse> result = new ArrayList<>();
        for (String link : recommends) {
//            System.out.println(link);

            if(productRepository.existsByPathId(link)){
                Product product = productRepository.findByPathId(link);
                result.add(productMapper.toProductResponse(product));
                if(result.size() == 20)break;
            }

        }
        return result;

    }

}
