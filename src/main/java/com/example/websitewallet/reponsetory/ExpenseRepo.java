package com.example.websitewallet.reponsetory;


import com.example.websitewallet.entity.Wallet.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long> {
    List<Expense> findAllByWalletId(Long walletId);


}