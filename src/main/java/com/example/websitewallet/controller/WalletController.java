package com.example.websitewallet.controller;


import com.example.websitewallet.dto.request.WalletCreateRequest;
import com.example.websitewallet.dto.request.WalletRequestTransfer;
import com.example.websitewallet.dto.request.WalletUpdateRequest;
import com.example.websitewallet.dto.response.ApiResponse;
import com.example.websitewallet.dto.response.WalletResponse;
import com.example.websitewallet.dto.response.WalletResponseTransfer;
import com.example.websitewallet.entity.Wallet.IconWallet;
import com.example.websitewallet.reponsetory.UserRepo;
import com.example.websitewallet.service.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@Slf4j
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {
    @Autowired
    WalletService walletService;

    @Autowired
    UserRepo userRepo;

    @PostMapping
    public ApiResponse<WalletResponse> createWallet(@ModelAttribute WalletCreateRequest request) {
        return ApiResponse.<WalletResponse>builder()
                .result(walletService.createWallet(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<WalletResponse> updateWallet(@RequestBody WalletUpdateRequest request, @PathVariable Long id) {
        WalletResponse walletResponse = walletService.updateWallet(id, request);
        return ApiResponse.<WalletResponse>builder()
                .result(walletResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{walletId}/share")
    public ResponseEntity<String> shareWallet(@PathVariable Long walletId, @RequestParam String userEmail, @RequestParam String permissionType) {
        try {
            walletService.shareWallet(walletId, userEmail, permissionType);
            return ResponseEntity.ok("Wallet shared successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public ApiResponse<List<WalletResponse>> getWallet() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("grantedAuthority : {}", grantedAuthority));
        return ApiResponse.<List<WalletResponse>>builder()
                .result(walletService.getWallets())
                .build();
    }

    @GetMapping("/{id}")
    public WalletResponse getWallet(@PathVariable("id") Long id) {
        return walletService.getWallet(id);
    }


    @GetMapping("/icon")
    public List<IconWallet> findAllIcon(){
        List<IconWallet> icon= walletService.findAllIcon();
        return icon;
    }
    @PostMapping("/transfer")
    public ApiResponse<WalletResponseTransfer> transferMoney(@RequestBody WalletRequestTransfer requestTransfer) {
        return ApiResponse.<WalletResponseTransfer>builder()
                .result(walletService.transferMoney(requestTransfer))
                .build();
    }
}
