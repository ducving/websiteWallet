package com.example.websitewallet.service;

import com.example.websitewallet.dto.request.BudgetCreateRequest;
import com.example.websitewallet.dto.request.BudgetUpdateRequest;
import com.example.websitewallet.dto.request.ClassifyCreateRequest;
import com.example.websitewallet.dto.request.ClassifyUpdateRequest;
import com.example.websitewallet.dto.response.BudgetResponse;
import com.example.websitewallet.dto.response.ClassifyResponse;
import com.example.websitewallet.entity.Budget;
import com.example.websitewallet.entity.Classify;
import com.example.websitewallet.entity.User;
import com.example.websitewallet.entity.Wallet.IconWallet;
import com.example.websitewallet.mapper.ClassifyMapper;
import com.example.websitewallet.reponsetory.BudgetRepo;
import com.example.websitewallet.reponsetory.ClassifyRepo;
import com.example.websitewallet.reponsetory.IconWalletRepo;
import com.example.websitewallet.reponsetory.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassifyService {
    @Autowired
    private ClassifyRepo classifyRepository;

    @Autowired
    private IconWalletRepo iconWalletRepo;

    @Autowired
    private ClassifyMapper classifyMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BudgetRepo budgetRepo;

    @Autowired
    private BudgetService budgetService;

    public ClassifyResponse addClassify(ClassifyCreateRequest request){
        Classify classify=classifyMapper.toClassify(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        classify.setUser(user);
        return classifyMapper.toClassifyResponse(classifyRepository.save(classify));
    }

    public ClassifyResponse updateClassify(Long id, ClassifyUpdateRequest request){
        Classify classify= classifyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        classifyMapper.updateClassy(classify,request);
        return classifyMapper.toClassifyResponse(classifyRepository.save(classify));
    }

    public List<ClassifyResponse> classifytlist(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        List<Classify> classifies = classifyRepository.findAllByUserEmail(currentUserEmail);
        return classifies.stream()
                .map(classifyMapper::toClassifyResponse)
                .collect(Collectors.toList());
    }

    @PostAuthorize("returnObject.user.email == authentication.name")
    public ClassifyResponse getClassify(Long id) {
        Classify classify = classifyRepository.findClassifyById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found or you are not the owner"));
        return classifyMapper.toClassifyResponse(classify);
    }


    @PreAuthorize("@budgetRepo.existsByIdAndUserEmail(#id, authentication.name)")
    public void deleteClassify(Long id){
        budgetRepo.deleteById(id);
    }

}
