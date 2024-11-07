package com.example.websitewallet.mapper;


import com.example.websitewallet.dto.request.ExpenseCreateRequest;
import com.example.websitewallet.dto.response.ExpenseResponse;
import com.example.websitewallet.entity.Wallet.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper{

    @Mapping(source = "wallet.id", target = "id_wallet")
    ExpenseResponse toExpenseResponse(Expense expense);


    Expense toExpense(ExpenseCreateRequest request);
}
