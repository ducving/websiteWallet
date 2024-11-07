package com.example.websitewallet.entity;

import com.example.websitewallet.entity.Wallet.IconWallet;
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
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    int budget;
    String description;
    @ManyToOne
    @JoinColumn(name = "id_classify")
    @JsonIgnore
    Classify classify;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    User user;
}
