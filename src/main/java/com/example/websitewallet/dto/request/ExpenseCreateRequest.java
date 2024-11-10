package com.example.websitewallet.dto.request;

import com.example.websitewallet.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenseCreateRequest {
    int amount;
    String description;
    Long id_wallet;
    User user;

}
