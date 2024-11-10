package com.example.websitewallet.controller;

import com.example.websitewallet.dto.request.BudgetCreateRequest;
import com.example.websitewallet.dto.request.BudgetUpdateRequest;
import com.example.websitewallet.dto.request.ClassifyCreateRequest;
import com.example.websitewallet.dto.request.WalletUpdateRequest;
import com.example.websitewallet.dto.response.ApiResponse;
import com.example.websitewallet.dto.response.BudgetResponse;
import com.example.websitewallet.dto.response.ClassifyResponse;
import com.example.websitewallet.dto.response.WalletResponse;
import com.example.websitewallet.entity.Budget;
import com.example.websitewallet.service.BudgetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/budget")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BudgetController {

    BudgetService budgetService;

    @PostMapping
    ApiResponse<BudgetResponse> add(@RequestBody BudgetCreateRequest request) {
        return ApiResponse.<BudgetResponse>builder()
                .result(budgetService.addBudget(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BudgetResponse> updateWallet(@RequestBody BudgetUpdateRequest request, @PathVariable Long id) {
        BudgetResponse budgetResponse = budgetService.updateBudget(id, request);
        return ApiResponse.<BudgetResponse>builder()
                .result(budgetResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<BudgetResponse>> getIncome() {
        return ApiResponse.<List<BudgetResponse>>builder()
                .result(budgetService.budgetlist())
                .build();
    }
    @GetMapping("/{id}")
    public BudgetResponse getBudget(@PathVariable("id") Long id) {
        return budgetService.getBudget(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
