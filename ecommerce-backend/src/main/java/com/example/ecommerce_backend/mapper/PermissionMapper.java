package com.example.ecommerce_backend.mapper;

import com.example.ecommerce_backend.dto.request.PermissionRequest;
import com.example.ecommerce_backend.dto.response.PermissionResponse;
import com.example.ecommerce_backend.models.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
