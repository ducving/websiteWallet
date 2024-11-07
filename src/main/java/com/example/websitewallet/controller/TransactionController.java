package com.example.websitewallet.controller;


import com.example.websitewallet.dto.request.ExpenseCreateRequest;
import com.example.websitewallet.dto.request.IncomeCreateRequest;
import com.example.websitewallet.dto.response.ApiResponse;
import com.example.websitewallet.dto.response.ExpenseResponse;
import com.example.websitewallet.dto.response.IncomeResponse;
import com.example.websitewallet.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {
    TransactionService transactionService;



    @PostMapping("/income")
    ApiResponse<IncomeResponse> createIncome(@RequestBody IncomeCreateRequest request)  {
        return ApiResponse.<IncomeResponse>builder()
                .result(transactionService.createIncome(request))
                .build();
    }

    @PostMapping("/expense")
    ApiResponse<ExpenseResponse> createExpense(@RequestBody ExpenseCreateRequest request)  {
        return ApiResponse.<ExpenseResponse>builder()
                .result(transactionService.createExpense(request))
                .build();
    }

}
