package com.example.websitewallet.controller;


import com.example.websitewallet.dto.request.*;
import com.example.websitewallet.dto.response.*;
import com.example.websitewallet.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/income")
    public ApiResponse<List<IncomeResponse>> getIncome() {
        return ApiResponse.<List<IncomeResponse>>builder()
                .result(transactionService.Incomelist())
                .build();
    }
    @PutMapping("/income/{id}")
    public ApiResponse<IncomeResponse> updateIncome(@RequestBody IncomeUpdateRequest request, @PathVariable Long id) {
        IncomeResponse incomeResponse = transactionService.updateIncome(id, request);
        return ApiResponse.<IncomeResponse>builder()
                .result(incomeResponse)
                .build();
    }
//expense
    @PostMapping("/expense")
    ApiResponse<ExpenseResponse> createExpense(@RequestBody ExpenseCreateRequest request)  {
        return ApiResponse.<ExpenseResponse>builder()
                .result(transactionService.createExpense(request))
                .build();
    }

    @PutMapping("/expense/{id}")
    public ApiResponse<ExpenseResponse> updateIncome(@RequestBody ExpenseUpdateRequest request, @PathVariable Long id) {
        ExpenseResponse expenseResponse = transactionService.updateExpense(id,request);
        return ApiResponse.<ExpenseResponse>builder()
                .result(expenseResponse)
                .build();
    }

    @GetMapping("/expense")
    public ApiResponse<List<ExpenseResponse>> getExpense() {
        return ApiResponse.<List<ExpenseResponse>>builder()
                .result(transactionService.findAll())
                .build();
    }
}
