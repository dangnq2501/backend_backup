package com.example.ecommerce_backend.mapper;

import com.example.ecommerce_backend.dto.request.ProductRequest;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    @Mapping(target="rating", source="productRequest.rating")
    Product toProduct(ProductRequest productRequest);
    @Mapping(target="id", source="product.id")

    ProductResponse toProductResponse(Product product);
    void updateItem(@MappingTarget Product product, ProductRequest productRequest);
}
