package com.example.websitewallet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ImageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
                .addResourceLocations("fileC:\\Users\\admin\\Downloads\\websiteWallet\\src\\main\\resources\\file_upload\\" );
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/" );
    }
}
