package com.example.websitewallet.dto.response;

import com.example.websitewallet.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassifyResponse {

    String name;
    String description;
    Long iconId;
    String transaction;
    User user;
}