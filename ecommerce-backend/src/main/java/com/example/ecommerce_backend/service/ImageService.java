package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.ImageRequest;
import com.example.ecommerce_backend.dto.response.ImageResponse;
import com.example.ecommerce_backend.mapper.ImageMapper;
import com.example.ecommerce_backend.models.Image;
import com.example.ecommerce_backend.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    ImageRepository imageRepository;
    ImageMapper imageMapper;

    public Image findByName(String path){
        Image image = imageRepository.findByPathImage(path);
        return image;
    }

    public ImageResponse create(ImageRequest imageRequest){
        Image image = imageMapper.toImage(imageRequest);
        ImageResponse imageResponse = imageMapper.toImageResponse(imageRepository.save(image));
        return imageResponse;
    }
}
