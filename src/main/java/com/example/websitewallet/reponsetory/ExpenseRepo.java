package com.example.websitewallet.reponsetory;


import com.example.websitewallet.entity.Wallet.Expense;
import com.example.websitewallet.entity.Wallet.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long> {
    List<Expense> findAllByWalletId(Long walletId);

    @Query("SELECT e FROM Income e JOIN e.user u WHERE u.email = :email")
    List<Expense> findAllByUserEmail(@Param("email") String email);

}