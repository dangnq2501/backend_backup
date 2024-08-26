package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.ImageRequest;
import com.example.ecommerce_backend.dto.response.ApiResponse;
import com.example.ecommerce_backend.dto.response.ImageResponse;
import com.example.ecommerce_backend.service.ImageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {
    ImageService imageService;

    @PostMapping()
    ApiResponse<ImageResponse> create(@RequestBody @Valid ImageRequest imageRequest){
        ApiResponse<ImageResponse> apiResponse = new ApiResponse<>();
        ImageResponse imageResponse = imageService.create(imageRequest);
        apiResponse.setResult(imageResponse);
        return apiResponse;
    }

}
