package com.example.websitewallet.mapper;

import com.example.websitewallet.dto.request.PermissionRequest;
import com.example.websitewallet.dto.response.PermissionResponse;
import com.example.websitewallet.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPerMission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}