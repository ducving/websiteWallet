package com.example.websitewallet.mapper;


import com.example.websitewallet.dto.request.WalletCreateRequest;
import com.example.websitewallet.dto.request.WalletUpdateRequest;
import com.example.websitewallet.dto.response.WalletResponse;
import com.example.websitewallet.entity.Wallet.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface WalletMapper {

    Wallet toWallet(WalletCreateRequest request);

    @Mapping(target = "iconId", source = "iconWallet.id")
    WalletResponse toWalletResponse(Wallet wallet);

    @Mapping(target = "user", ignore = true)
    void updateWallet(@MappingTarget Wallet wallet, WalletUpdateRequest request);
}

