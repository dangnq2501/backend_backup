package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.UserCreationRequest;
import com.example.ecommerce_backend.dto.request.UserUpdateRequest;
import com.example.ecommerce_backend.dto.response.ApiResponse;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.dto.response.UserResponse;
import com.example.ecommerce_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/getAll")
    ApiResponse<List<UserResponse>> getAllUsers() {

        List<UserResponse> responses = userService.getUsers();
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(responses);
        return apiResponse;
    }
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request)  {

        UserResponse userResponse = userService.createUser(request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserUpdateRequest request)  {

        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);
        return apiResponse;
    }

    @PutMapping
    ApiResponse<UserResponse> updateUser( @RequestBody @Valid UserUpdateRequest request)  {
//        System.out.println("Update User");
        UserResponse userResponse = userService.updateUser(request);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);
        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable("id") UUID id)  {
        UserResponse userResponse = userService.getUserById(id);
        System.out.println(userResponse.getCart_id());
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);
        return apiResponse;
    }


    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo (){
        UserResponse userResponse = userService.getMyInfo();
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);
        return apiResponse;
    }
    @DeleteMapping("/{id}")
    ApiResponse<Object> deteleUser(@PathVariable("id") UUID id)  {
        userService.deleteUser(id);
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("User has been deleted!");
        return apiResponse;
    }

    @GetMapping("/recommend")
    ApiResponse<String> recommend_check()  {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        String s = "healthy";
        apiResponse.setResult(s);
        return apiResponse;
    }

    @GetMapping("/recommend/{user_id}")
    ApiResponse<List<ProductResponse>> recommend(@PathVariable("user_id") UUID user_id)  {
//        System.out.println("Recommend");
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        List<ProductResponse> result = userService.recommend(user_id);
        apiResponse.setResult(result);
        return apiResponse;
    }


}
