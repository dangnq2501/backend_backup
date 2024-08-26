package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.PermissionRequest;
import com.example.ecommerce_backend.dto.response.PermissionResponse;
import com.example.ecommerce_backend.mapper.PermissionMapper;
import com.example.ecommerce_backend.models.Permission;
import com.example.ecommerce_backend.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission((request));
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAll(){
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(String name){
        permissionRepository.deleteById(name);
    }

}
