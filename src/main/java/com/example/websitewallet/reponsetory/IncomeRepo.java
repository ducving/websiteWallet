package com.example.websitewallet.reponsetory;



import com.example.websitewallet.entity.Wallet.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepo extends JpaRepository<Income,Long> {
    List<Income> findAllByWalletId(Long walletId);

}
