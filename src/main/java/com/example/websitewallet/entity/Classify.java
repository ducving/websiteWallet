package com.example.websitewallet.entity;

import com.example.websitewallet.entity.Wallet.Expense;
import com.example.websitewallet.entity.Wallet.IconWallet;
import com.example.websitewallet.entity.Wallet.Income;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "classify")
public class Classify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    String transaction;
    @ManyToOne
    @JoinColumn(name = "id_icon")
    @JsonIgnore
    IconWallet iconWallet;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    User user;

}
