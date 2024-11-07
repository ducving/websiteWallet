package com.example.websitewallet.mapper;

import com.example.websitewallet.dto.request.ClassifyCreateRequest;
import com.example.websitewallet.dto.request.ClassifyUpdateRequest;

import com.example.websitewallet.dto.response.ClassifyResponse;
import com.example.websitewallet.entity.Classify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClassifyMapper {

    @Mapping(target = "iconWallet.id", source = "iconId")
    Classify toClassify(ClassifyCreateRequest request);

    @Mapping(target = "iconId", source = "iconWallet.id")
    ClassifyResponse toClassifyResponse(Classify Classify);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "iconWallet", ignore = true)
    void updateClassy(@MappingTarget Classify classify, ClassifyUpdateRequest request);

}
