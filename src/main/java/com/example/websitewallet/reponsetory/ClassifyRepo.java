package com.example.websitewallet.reponsetory;
import com.example.websitewallet.entity.Budget;
import com.example.websitewallet.entity.Classify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassifyRepo extends JpaRepository<Classify,Long> {
    @Query("SELECT c FROM Classify c WHERE c.id = :id")
    Optional<Classify> findClassifyById(@Param("id") Long id);
    @Query("SELECT c FROM Classify c JOIN c.user u WHERE u.email = :email")
    List<Classify> findAllByUserEmail(@Param("email") String email);
}
