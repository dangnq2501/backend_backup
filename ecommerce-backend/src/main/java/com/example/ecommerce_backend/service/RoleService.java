package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.RoleRequest;
import com.example.ecommerce_backend.dto.response.RoleResponse;
import com.example.ecommerce_backend.mapper.RoleMapper;
import com.example.ecommerce_backend.models.Permission;
import com.example.ecommerce_backend.models.Role;
import com.example.ecommerce_backend.repository.PermissionRepository;
import com.example.ecommerce_backend.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse create(RoleRequest request){
        Role role = roleMapper.toRole(request);
        if(request.getPermissions() != null){
            List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
            role.setPermissions( new HashSet<>(permissions));
        }
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAll(){
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteId(String name){
        roleRepository.deleteById(name);
    }
}
