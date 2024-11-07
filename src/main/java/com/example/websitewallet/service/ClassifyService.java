package com.example.websitewallet.service;

import com.example.websitewallet.dto.request.ClassifyCreateRequest;
import com.example.websitewallet.dto.request.ClassifyUpdateRequest;
import com.example.websitewallet.dto.response.ClassifyResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

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
  
    public ClassifyResponse addClassify(ClassifyCreateRequest request) {
        Classify classify=classifyMapper.toClassify(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        IconWallet iconWallet = iconWalletRepo.findById(request.getIconId())
                .orElseThrow(() -> new RuntimeException("Icon not found"));
        classify.setIconWallet(iconWallet);
        classify.setUser(user);
        return classifyMapper.toClassifyResponse(classifyRepository.save(classify));
    }

    public ClassifyResponse updateClassify(Long idClassify,ClassifyUpdateRequest request){
        Classify classify= classifyRepository.findById(idClassify)
                .orElseThrow(() -> new RuntimeException("User not found"));
        classifyMapper.updateClassy(classify,request);
        IconWallet iconWallet = iconWalletRepo.findById(request.getIconId())
                .orElseThrow(() -> new RuntimeException("Icon not found"));
        classify.setIconWallet(iconWallet);
        return classifyMapper.toClassifyResponse(classifyRepository.save(classify));
    }


    @Transactional
    public void deleteClassify(Long id) {
        Classify classify = classifyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classify not found"));
        budgetService.deleteAllBudgetsByClassifyId(id);
        budgetService.deleteAllBudgetsByClassifyId(id);
        // Xóa classify khỏi cơ sở dữ liệu
        classifyRepository.deleteById(id);
    }


    public List<Classify> getClassify() {
        return classifyRepository.findAll();
    }



}
