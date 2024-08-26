package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.ProductRequest;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.exception.ErrorCode;
import com.example.ecommerce_backend.mapper.ProductMapper;
import com.example.ecommerce_backend.models.Image;
import com.example.ecommerce_backend.models.Product;
import com.example.ecommerce_backend.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    ImageService imageService;
    public List<ProductResponse> getAll(){
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }

    public ProductResponse findById(UUID id){
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        return productMapper.toProductResponse(product);
    }
    public List<ProductResponse> findByRangePrice(double minPrice, double maxPrice){
        return productRepository.findAllByPriceBetween(minPrice, maxPrice).stream().map(productMapper::toProductResponse).toList();
    }

    public List<ProductResponse> findByRangeRating(double minRate, double maxRate){
        return productRepository.findAllByRatingBetween(minRate, maxRate).stream().map(productMapper::toProductResponse).toList();
    }

    public List<ProductResponse> findByName(String name){
        return productRepository.findAllByName(name).stream().map(productMapper::toProductResponse).toList();
    }
    private String cleanString(String input) {
        return (input == null ? "" : input.replaceAll("\\s+", "_"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse create(ProductRequest productRequest){
        Product product = productMapper.toProduct(productRequest);
//        System.out.println(product.getName());
//        System.out.println(product.getDescription());

        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse delete(UUID id){
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        if(product.getStockQuantity() > 0){
            product.setStockQuantity(product.getStockQuantity()-1);
        }
        else{
            throw  new AppException((ErrorCode.PRODUCT_OUT_OF_STOCK));
        }
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse update(UUID id, ProductRequest productRequest){
//        System.out.println(id);
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        productMapper.updateItem(product, productRequest);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse findByImage(String image_file){
        Image image = imageService.findByName(image_file);
        Product product = productRepository.findByPathId(image.getPathId());
        return productMapper.toProductResponse(product);
    }



}
