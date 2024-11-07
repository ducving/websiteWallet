package com.example.websitewallet.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletRequestTransfer {
    Long idWalletSend;
    Long idWalletReceive;
    int amount;
}
