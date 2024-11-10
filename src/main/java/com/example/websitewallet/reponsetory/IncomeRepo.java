package com.example.websitewallet.reponsetory;



import com.example.websitewallet.entity.Budget;
import com.example.websitewallet.entity.Wallet.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepo extends JpaRepository<Income,Long> {
    List<Income> findAllByWalletId(Long walletId);

    @Query("SELECT i FROM Income i JOIN i.user u WHERE u.email = :email")
    List<Income> findAllByUserEmail(@Param("email") String email);
}
