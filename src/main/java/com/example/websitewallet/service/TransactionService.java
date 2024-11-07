package com.example.websitewallet.service;


import com.example.websitewallet.dto.request.ExpenseCreateRequest;
import com.example.websitewallet.dto.request.IncomeCreateRequest;
import com.example.websitewallet.dto.response.ExpenseResponse;
import com.example.websitewallet.dto.response.IncomeResponse;
import com.example.websitewallet.entity.Wallet.Expense;
import com.example.websitewallet.entity.Wallet.Income;
import com.example.websitewallet.entity.Wallet.Wallet;
import com.example.websitewallet.exception.AppException;
import com.example.websitewallet.exception.ErrorCode;
import com.example.websitewallet.mapper.ExpenseMapper;
import com.example.websitewallet.mapper.IncomeMapper;
import com.example.websitewallet.reponsetory.ExpenseRepo;
import com.example.websitewallet.reponsetory.IncomeRepo;
import com.example.websitewallet.reponsetory.WalletRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    private IncomeRepo incomeRepository;
    @Autowired
    private WalletRepo walletRepository;
    @Autowired
    private IncomeMapper incomeMapper;
    @Autowired
    private ExpenseMapper expenseMapper;
    @Autowired
    private ExpenseRepo expenseRepo;

    // Tạo khoản thu mới
    public IncomeResponse createIncome(IncomeCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWithoutSeconds = now.truncatedTo(ChronoUnit.MINUTES);
        if (request.getId_wallet() == null) {
            throw new RuntimeException("Wallet ID cannot be null");
        }
        Wallet wallet = walletRepository.findById(request.getId_wallet())
                .orElseThrow(() -> new RuntimeException("Icon not found"));
        Income income = incomeMapper.toIncome(request);
        income.setDate(nowWithoutSeconds);
        wallet.setPrice(wallet.getPrice()+income.getAmount());
        income.setWallet(wallet);
        Income savedIncome = incomeRepository.save(income);
            return incomeMapper.toIncomeResponse(savedIncome);
    }

    public ExpenseResponse createExpense(ExpenseCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWithoutSeconds = now.truncatedTo(ChronoUnit.MINUTES);

        if (request.getId_wallet() == null) {
            throw new RuntimeException("Wallet ID cannot be null");
        }

        Wallet wallet = walletRepository.findById(request.getId_wallet())
                .orElseThrow(() -> new RuntimeException("Icon not found"));

        Expense expense = expenseMapper.toExpense(request);
        expense.setDate(nowWithoutSeconds);

        if (wallet.getPrice()!=0 && wallet.getPrice()>expense.getAmount()){
            wallet.setPrice(wallet.getPrice()-expense.getAmount());
        }else {
            throw new AppException(ErrorCode.INSUFFICIENT_FUNDS);
        }

        expense.setWallet(wallet);
        Expense savedExpense = expenseRepo.save(expense);
        return expenseMapper.toExpenseResponse(savedExpense);
    }

    public void deleteIncome(Long walletId){
        List<Income> incomes = incomeRepository.findAllByWalletId(walletId);
        if (incomes.isEmpty()) {
            return;
        }
        incomeRepository.deleteAll(incomes);
    }
    public void deleteExpense(Long walletId) {
        List<Expense> expenses = expenseRepo.findAllByWalletId(walletId);
        if (expenses.isEmpty()) {
            return;
        }
        expenseRepo.deleteAll(expenses);
    }



}
