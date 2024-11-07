package com.example.websitewallet.service;


import com.example.websitewallet.dto.request.WalletCreateRequest;
import com.example.websitewallet.dto.request.WalletRequestTransfer;
import com.example.websitewallet.dto.request.WalletUpdateRequest;
import com.example.websitewallet.dto.response.WalletResponse;
import com.example.websitewallet.dto.response.WalletResponseTransfer;
import com.example.websitewallet.entity.User;
import com.example.websitewallet.entity.Wallet.IconWallet;
import com.example.websitewallet.entity.Wallet.Wallet;
import com.example.websitewallet.exception.ResourceNotFoundException;
import com.example.websitewallet.mapper.WalletMapper;
import com.example.websitewallet.reponsetory.IconWalletRepo;
import com.example.websitewallet.reponsetory.PermissionRepo;
import com.example.websitewallet.reponsetory.UserRepo;
import com.example.websitewallet.reponsetory.WalletRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class WalletService {
    @Autowired
    private PermissionRepo permissionRepo;

    @Value("${file-upload}")
    String fileUpload;

    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired

    private TransactionService transactionService;
    @Autowired
    private UserService userService;


    @Autowired
    private IconWalletRepo iconWalletRepo;

    @PreAuthorize("hasRole('ADMIN')")
    public WalletResponse createWallet(WalletCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWithoutSeconds = now.truncatedTo(ChronoUnit.MINUTES);
        Wallet wallet = walletMapper.toWallet(request);
        wallet.setCreatedAt(nowWithoutSeconds);
        // Lấy thông tin người dùng đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Lấy icon theo ID từ request
        IconWallet iconWallet = iconWalletRepo.findById(request.getIconId())
                .orElseThrow(() -> new RuntimeException("Icon not found"));
        // Gắn icon vào ví
        wallet.setIconWallet(iconWallet);
        wallet.setUser(user);
        return walletMapper.toWalletResponse(walletRepo.save(wallet));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public WalletResponse updateWallet(Long id, WalletUpdateRequest request) {
        Wallet wallet = walletRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        walletMapper.updateWallet(wallet, request);
        if (request.getIconId() != null) {
            IconWallet iconWallet = iconWalletRepo.findById(request.getIconId())
                    .orElseThrow(() -> new RuntimeException("Icon not found"));
            wallet.setIconWallet(iconWallet);
        }
        Wallet updatedWallet = walletRepo.save(wallet);
        return walletMapper.toWalletResponse(updatedWallet);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteWallet(Long id) {
        Wallet wallet = walletRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        transactionService.deleteExpense(id);
        transactionService.deleteIncome(id);
        walletRepo.save(wallet);
        walletRepo.deleteById(id);
    }


    public void shareWallet(Long id, String userEmail, String permissionType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        // Tìm kiếm ví theo ID
        Wallet wallet = walletRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<WalletResponse> getWallets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        List<Wallet> wallets = walletRepo.findAllByUserEmail(currentUserEmail);
        return wallets.stream()
                .map(walletMapper::toWalletResponse)
                .collect(Collectors.toList());
    }


    @PostAuthorize("returnObject.user.email == authentication.name")
    @PreAuthorize("hasRole('ADMIN')")
    public WalletResponse getWallet(Long id) {
        Wallet wallet = walletRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found or you are not the owner"));
        return walletMapper.toWalletResponse(wallet);
    }

    public List<IconWallet> findAllIcon() {
        return iconWalletRepo.findAll();
    }


    public WalletResponseTransfer transferMoney(WalletRequestTransfer request) {
        Wallet sourceWallet = walletRepo.findById(request.getIdWalletSend())
                .orElseThrow(() -> new ResourceNotFoundException("Source wallet not found"));

        if (sourceWallet.getPrice() < request.getAmount()) {

        }
        // Tìm ví đích
        Wallet destinationWallet = walletRepo.findById(request.getIdWalletReceive())
                .orElseThrow(() -> new ResourceNotFoundException("Destination wallet not found"));

        // Trừ tiền từ ví nguồn
        sourceWallet.setPrice(sourceWallet.getPrice() - request.getAmount());
        walletRepo.save(sourceWallet);

        // Cộng tiền vào ví đích
        destinationWallet.setPrice(destinationWallet.getPrice() + request.getAmount());
        walletRepo.save(destinationWallet);

        // Tạo đối tượng response
        WalletResponseTransfer response = new WalletResponseTransfer();
        response.setIdWalletSend(sourceWallet.getId());
        response.setIdWalletReceive(destinationWallet.getId());
        response.setAmount(request.getAmount());

        return response;
    }

}



