package com.example.websitewallet.service;


import com.example.websitewallet.dto.request.PermissionRequest;
import com.example.websitewallet.dto.response.PermissionResponse;
import com.example.websitewallet.entity.Permission;
import com.example.websitewallet.mapper.PermissionMapper;
import com.example.websitewallet.reponsetory.PermissionRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepo permissionRepo;

    PermissionMapper permissionMapper;



   public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPerMission(request);
        permission = permissionRepo.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

   public List<PermissionResponse> findAll(){
        var permissions = permissionRepo.findAll();
      return   permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

   public void delete(String permission){
        permissionRepo.deleteById(permission);
    }
}
