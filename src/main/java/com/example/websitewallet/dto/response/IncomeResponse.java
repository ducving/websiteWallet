package com.example.websitewallet.dto.response;

import com.example.websitewallet.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IncomeResponse {
    Long id;
    int amount;
    String description;
    Long id_wallet;
    User user;
}
