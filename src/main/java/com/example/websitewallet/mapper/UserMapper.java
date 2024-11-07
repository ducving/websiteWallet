package com.example.websitewallet.mapper;


import com.example.websitewallet.dto.request.UserCreationRequest;
import com.example.websitewallet.dto.request.UserUpdateRequest;
import com.example.websitewallet.dto.response.UserResponse;
import com.example.websitewallet.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
