package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.ProductRequest;
import com.example.ecommerce_backend.dto.response.ApiResponse;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.service.ImageService;
import com.example.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;
    ImageService imageService;

    @GetMapping("/getAll")
    ApiResponse<List<ProductResponse>> getAll(){
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        List<ProductResponse> products = productService.getAll();
        apiResponse.setResult(products);
        return apiResponse;
    }

    @GetMapping("/getByPriceRange/{minPrice}/{maxPrice}")
    ApiResponse<List<ProductResponse>> getByPriceRange(@PathVariable("minPrice") double minPrice,
                                                       @PathVariable("maxPrice") double maxPrice){
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        List<ProductResponse> products = productService.findByRangePrice(minPrice, maxPrice);
        apiResponse.setResult(products);
        return apiResponse;
    }

    @GetMapping("/getByRatingRange/{minRate}/{maxRate}")
    ApiResponse<List<ProductResponse>> getByRatingRange(@PathVariable("minRate") double minRate,
                                                       @PathVariable("maxRate") double maxRate){
//        System.out.println(minRate);
//        System.out.println(maxRate);
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        List<ProductResponse> products = productService.findByRangeRating(minRate, maxRate);
        apiResponse.setResult(products);
        return apiResponse;
    }

    @GetMapping("/getByName/{name}")
    ApiResponse<List<ProductResponse>> getByName(@PathVariable("name") String name){
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();

        List<ProductResponse> products = productService.findByName(name);
        System.out.println(name);
        apiResponse.setResult(products);
        return apiResponse;
    }

    @PostMapping()
    ApiResponse<ProductResponse> create(@RequestBody @Valid ProductRequest itemRequest){
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        ProductResponse itemResponse = productService.create(itemRequest);
        apiResponse.setResult(itemResponse);
        return apiResponse;
    }

    @DeleteMapping(("{id}"))
    ApiResponse<Object> detele(@PathVariable("id") UUID id){
        ApiResponse< Object> apiResponse = new ApiResponse<>();
        productService.delete(id);
        apiResponse.setMessage("Product has been deleted!");
        return apiResponse;

    }

    @PutMapping("{id}")
    ApiResponse<ProductResponse> update(@PathVariable("id") UUID id, @RequestBody @Valid ProductRequest productRequest){
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        ProductResponse productResponse = productService.update(id, productRequest);
        apiResponse.setResult(productResponse);
        return apiResponse;

    }

    @GetMapping("{id}")
    ApiResponse<ProductResponse> findById(@PathVariable("id") UUID id){
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        System.out.println(id);
        ProductResponse productResponse = productService.findById(id);
        apiResponse.setResult(productResponse);
        return apiResponse;

    }

    @GetMapping("{image_file}")
    ApiResponse<ProductResponse> getByImage(@PathVariable("image_file") String image_file){

        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        ProductResponse productResponse = productService.findByImage(image_file);
        apiResponse.setResult(productResponse);
        return apiResponse;

    }
}
