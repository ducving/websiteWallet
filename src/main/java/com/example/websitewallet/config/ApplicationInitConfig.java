package com.example.websitewallet.config;
import java.util.HashSet;

import com.example.websitewallet.entity.User;
import com.example.websitewallet.enums.RoleEnum;
import com.example.websitewallet.reponsetory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepo userRpo) {
        return args -> {
            if (userRpo.findByEmail("admin@gmail.com").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(RoleEnum.ADMIN.name());
                User user = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("123123"))
                        .build();
                userRpo.save(user);
            }
        };
    }
}