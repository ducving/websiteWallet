package com.example.websitewallet.service;


import com.example.websitewallet.dto.request.ExpenseCreateRequest;
import com.example.websitewallet.dto.request.ExpenseUpdateRequest;
import com.example.websitewallet.dto.request.IncomeCreateRequest;
import com.example.websitewallet.dto.request.IncomeUpdateRequest;
import com.example.websitewallet.dto.response.BudgetResponse;
import com.example.websitewallet.dto.response.ExpenseResponse;
import com.example.websitewallet.dto.response.IncomeResponse;
import com.example.websitewallet.entity.Budget;
import com.example.websitewallet.entity.User;
import com.example.websitewallet.entity.Wallet.Expense;
import com.example.websitewallet.entity.Wallet.IconWallet;
import com.example.websitewallet.entity.Wallet.Income;
import com.example.websitewallet.entity.Wallet.Wallet;
import com.example.websitewallet.exception.AppException;
import com.example.websitewallet.exception.ErrorCode;
import com.example.websitewallet.mapper.ExpenseMapper;
import com.example.websitewallet.mapper.IncomeMapper;
import com.example.websitewallet.reponsetory.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WalletRepo walletRepo;

    //khoản thu
    public IncomeResponse createIncome(IncomeCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWithoutSeconds = now.truncatedTo(ChronoUnit.MINUTES);
        Income income=incomeMapper.toIncome(request);
        income.setDate(nowWithoutSeconds);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Lấy icon theo ID từ request
        Wallet wallet = walletRepository.findById(request.getId_wallet())
                .orElseThrow(() -> new RuntimeException("Icon not found"));
        wallet.setPrice(income.getAmount()+wallet.getPrice());
        income.setUser(user);
        income.setWallet(wallet);
        return incomeMapper.toIncomeResponse(incomeRepository.save(income));

    }

    public List<IncomeResponse> Incomelist(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        List<Income> incomes = incomeRepository.findAllByUserEmail(currentUserEmail);
        return incomes.stream()
                .map(incomeMapper::toIncomeResponse)
                .collect(Collectors.toList());
    }

    public IncomeResponse updateIncome(Long id,IncomeUpdateRequest request){
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Long walletId = request.getId_wallet();
        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        income.setWallet(wallet);
        return incomeMapper.toIncomeResponse(incomeRepository.save(income));
    }

    public void deleteIncome(Long id){
        List<Income> incomes = incomeRepository.findAllByWalletId(id);
        if (incomes.isEmpty()) {
            return;
        }
        incomeRepository.deleteAll(incomes);
    }



    //khoản chi
    public ExpenseResponse createExpense(ExpenseCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWithoutSeconds = now.truncatedTo(ChronoUnit.MINUTES);
        Expense expense=expenseMapper.toExpense(request);
        expense.setDate(nowWithoutSeconds);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Lấy icon theo ID từ request
        Wallet wallet = walletRepository.findById(request.getId_wallet())
                .orElseThrow(() -> new RuntimeException("Icon not found"));
        if (wallet.getPrice()<=0){
            throw new AppException(ErrorCode.INSUFFICIENT_FUNDS);
        }else {
            wallet.setPrice(wallet.getPrice()-expense.getAmount());
        }
        expense.setUser(user);
        expense.setWallet(wallet);
        return expenseMapper.toExpenseResponse(expenseRepo.save(expense));

    }
    public void deleteExpense(Long walletId) {
        List<Expense> expenses = expenseRepo.findAllByWalletId(walletId);
        if (expenses.isEmpty()) {
            return;
        }
        expenseRepo.deleteAll(expenses);
    }

    public ExpenseResponse updateExpense(Long id, ExpenseUpdateRequest request){
        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        Long walletId = request.getId_wallet();
        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        expense.setWallet(wallet);
        return expenseMapper.toExpenseResponse(expenseRepo.save(expense));
    }

    public List<ExpenseResponse> findAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        List<Expense> expenses = expenseRepo.findAllByUserEmail(currentUserEmail);
        return expenses.stream()
                .map(expenseMapper::toExpenseResponse)
                .collect(Collectors.toList());
    }
}
