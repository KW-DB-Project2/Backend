package com.example.db.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173") // 허용할 클라이언트 도메인
                .allowedOrigins("http://43.200.199.72:5173") // 허용할 클라이언트 도메인
                .allowedOrigins("https://dslquant.site")
                .allowedOrigins("https://www.dslquant.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                //.allowedHeaders("Content-Type")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "authorization")
                .allowCredentials(true);
    }
}
