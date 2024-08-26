package com.example.ecommerce_backend.mapper;

import com.example.ecommerce_backend.dto.request.RoleRequest;
import com.example.ecommerce_backend.dto.response.RoleResponse;
import com.example.ecommerce_backend.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
    @Mapping(target="permissions", ignore=true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);

}
