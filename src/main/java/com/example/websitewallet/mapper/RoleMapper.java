package com.example.websitewallet.mapper;

import com.example.websitewallet.dto.request.RoleRequest;
import com.example.websitewallet.dto.response.RoleResponse;
import com.example.websitewallet.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target ="permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
