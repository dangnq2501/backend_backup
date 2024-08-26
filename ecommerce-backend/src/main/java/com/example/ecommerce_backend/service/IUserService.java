package com.example.ecommerce_backend.service;


import com.example.ecommerce_backend.dto.request.UserCreationRequest;
import com.example.ecommerce_backend.dto.request.UserUpdateRequest;
import com.example.ecommerce_backend.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    UserResponse createUser(UserCreationRequest userRequest);

    UserResponse updateUser(UUID id, UserUpdateRequest userRequest);

    UserResponse updateUser(UserUpdateRequest userRequest);

    UserResponse getUserById(UUID id);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    void deleteUser(UUID id);
}