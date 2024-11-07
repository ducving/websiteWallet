package com.example.websitewallet.service;



import com.example.websitewallet.dto.request.RoleRequest;
import com.example.websitewallet.dto.response.RoleResponse;
import com.example.websitewallet.mapper.RoleMapper;
import com.example.websitewallet.reponsetory.PermissionRepo;
import com.example.websitewallet.reponsetory.RoleRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepo roleRepo;
    PermissionRepo permissionRepo;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permission = permissionRepo.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        roleRepo.save(role);
        return roleMapper.toRoleResponse(role);
    }


    public List<RoleResponse> findAll(){
        return roleRepo.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String role) {
        roleRepo.deleteById(role);
    }
}