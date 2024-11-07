package com.example.websitewallet.entity.Wallet;

import com.example.websitewallet.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    int price;
    String description;
    LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    User user;

    @ManyToOne
    @JoinColumn(name = "id_icon")
    @JsonIgnore
    IconWallet iconWallet;




}
