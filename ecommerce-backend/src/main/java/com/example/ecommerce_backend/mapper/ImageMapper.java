package com.example.ecommerce_backend.mapper;

import com.example.ecommerce_backend.dto.request.ImageRequest;
import com.example.ecommerce_backend.dto.response.ImageResponse;
import com.example.ecommerce_backend.models.Image;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface ImageMapper {
    Image toImage(ImageRequest imageRequest);
    ImageResponse toImageResponse(Image image);
}
