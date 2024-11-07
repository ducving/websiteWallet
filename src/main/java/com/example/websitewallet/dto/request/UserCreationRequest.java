package com.example.websitewallet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 4,message = "USERNAME_INVALID")
    String username;
    @Size(min = 6,message = "PASSWORD_INVALID")
    String password;

    String name;
    @Email(message = "EMAIL_INVALID")
    String email;
    String phone;

}
