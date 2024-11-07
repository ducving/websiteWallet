package com.example.websitewallet.reponsetory;

import com.example.websitewallet.entity.Budget;
import com.example.websitewallet.entity.Wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepo extends JpaRepository<Budget,Long> {
    @Query("SELECT b FROM Budget b WHERE b.id = :id")
    Optional<Budget> findBudgetById(@Param("id") Long id);

    @Query("SELECT b FROM Budget b JOIN b.user u WHERE u.email = :email")
    List<Budget> findAllByUserEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Budget b WHERE b.id = :id AND b.user.email = :email")
    boolean existsByIdAndUserEmail(@Param("id") Long id, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Budget b WHERE b.classify.id = :classifyId")
    void deleteAllByClassifyId(@Param("classifyId") Long classifyId);

}
