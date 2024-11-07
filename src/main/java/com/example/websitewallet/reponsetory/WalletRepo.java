package com.example.websitewallet.reponsetory;


import com.example.websitewallet.entity.Wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet,Long> {
    @Query("SELECT w FROM Wallet w JOIN w.user u WHERE u.email = :email")
    List<Wallet> findAllByUserEmail(@Param("email") String email);

}
