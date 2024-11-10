package com.example.websitewallet.mapper;

import com.example.websitewallet.dto.request.IncomeCreateRequest;
import com.example.websitewallet.dto.request.IncomeUpdateRequest;
import com.example.websitewallet.dto.request.WalletUpdateRequest;
import com.example.websitewallet.dto.response.IncomeResponse;
import com.example.websitewallet.entity.Wallet.Income;
import com.example.websitewallet.entity.Wallet.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    @Mapping(source = "wallet.id", target = "id_wallet")
    IncomeResponse toIncomeResponse(Income income);
    Income toIncome(IncomeCreateRequest request);
    @Mapping(target = "user", ignore = true)
    void updateIncome(@MappingTarget Income income, IncomeUpdateRequest request);
}
