package com.example.websitewallet.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IncomeCreateRequest {
    int amount;
    String description;
    Long id_wallet;
}
