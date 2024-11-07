package com.example.websitewallet.reponsetory;


import com.example.websitewallet.entity.Wallet.IconWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconWalletRepo extends JpaRepository<IconWallet,Long> {


}
