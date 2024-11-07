package com.example.websitewallet.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletResponseTransfer {
    Long idWalletSend;
    Long idWalletReceive;
    int amount;
}
