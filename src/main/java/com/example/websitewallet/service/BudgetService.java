package com.example.websitewallet.service;

import com.example.websitewallet.dto.request.BudgetCreateRequest;
import com.example.websitewallet.dto.request.BudgetUpdateRequest;
import com.example.websitewallet.dto.response.BudgetResponse;
import com.example.websitewallet.entity.Budget;
import com.example.websitewallet.entity.User;
import com.example.websitewallet.mapper.BudgetMapper;
import com.example.websitewallet.reponsetory.BudgetRepo;
import com.example.websitewallet.reponsetory.ClassifyRepo;
import com.example.websitewallet.reponsetory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {
    @Autowired
    private BudgetMapper budgetMapper;
    @Autowired
    private BudgetRepo budgetRepo;

    @Autowired
    private UserRepo userRepo;

    public BudgetResponse addBudget(BudgetCreateRequest request){
        Budget budget=budgetMapper.tobudget(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        budget.setUser(user);
        return budgetMapper.toBudgetResponse(budgetRepo.save(budget));
    }

    public BudgetResponse updateBudget(Long id,BudgetUpdateRequest request){
        Budget budget= budgetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        budgetMapper.updateBudget(budget,request);
        return budgetMapper.toBudgetResponse(budgetRepo.save(budget));
    }

    public List<BudgetResponse> budgetlist(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        List<Budget> budgets = budgetRepo.findAllByUserEmail(currentUserEmail);
        return budgets.stream()
                .map(budgetMapper::toBudgetResponse)
                .collect(Collectors.toList());
    }

    @PostAuthorize("returnObject.user.email == authentication.name")
    public BudgetResponse getBudget(Long id) {
        Budget budget = budgetRepo.findBudgetById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found or you are not the owner"));
        return budgetMapper.toBudgetResponse(budget);
    }


    @PreAuthorize("@budgetRepo.existsByIdAndUserEmail(#id, authentication.name)")
    public void deleteBudget(Long id){
        budgetRepo.deleteById(id);
    }




}
