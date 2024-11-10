package com.example.websitewallet.controller;

import com.example.websitewallet.dto.request.ClassifyCreateRequest;
import com.example.websitewallet.dto.request.ClassifyUpdateRequest;
import com.example.websitewallet.dto.response.ApiResponse;
import com.example.websitewallet.dto.response.BudgetResponse;
import com.example.websitewallet.dto.response.ClassifyResponse;
import com.example.websitewallet.dto.response.WalletResponse;
import com.example.websitewallet.entity.Classify;
import com.example.websitewallet.service.ClassifyService;
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
@RequestMapping("/classify")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassifyController {

    ClassifyService classifyService;


    @PostMapping
    ApiResponse<ClassifyResponse> add(@ModelAttribute ClassifyCreateRequest request) {
        return ApiResponse.<ClassifyResponse>builder()
                .result(classifyService.addClassify(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ClassifyResponse> update(@RequestBody ClassifyUpdateRequest request,@PathVariable Long id) {
        return ApiResponse.<ClassifyResponse>builder()
                .result(classifyService.updateClassify(id,request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ClassifyResponse>> getClassify() {
        return ApiResponse.<List<ClassifyResponse>>builder()
                .result(classifyService.classifytlist())
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassify(@PathVariable Long id) {
        classifyService.deleteClassify(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ClassifyResponse getBudget(@PathVariable("id") Long id) {
        return classifyService.getClassify(id);
    }
}
