package com.example.websitewallet.entity.Wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "inconme")
public class Income{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    int amount;
    LocalDateTime date;
    String description;

    @ManyToOne
    @JoinColumn(name = "id_wallet")
    @JsonIgnore
    Wallet wallet;
}