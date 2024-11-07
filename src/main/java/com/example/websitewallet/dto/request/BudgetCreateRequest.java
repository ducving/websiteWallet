package com.example.websitewallet.dto.request;

import com.example.websitewallet.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetCreateRequest {
    String name;
    String description;
    int budget;
    Long id_classify;
    User user;
}
