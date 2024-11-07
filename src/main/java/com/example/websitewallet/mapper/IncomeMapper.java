package com.example.websitewallet.mapper;

import com.example.websitewallet.dto.request.IncomeCreateRequest;
import com.example.websitewallet.dto.response.IncomeResponse;
import com.example.websitewallet.entity.Wallet.Income;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IncomeMapper {

    @Mapping(source = "wallet.id", target = "id_wallet")
    IncomeResponse toIncomeResponse(Income income);


    Income toIncome(IncomeCreateRequest request);
}
