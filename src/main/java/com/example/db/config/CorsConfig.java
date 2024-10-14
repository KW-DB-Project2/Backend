package com.example.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");  // 프론트엔드 서버의 주소
//        config.addAllowedOrigin("http://43.200.199.72:5173");  // 프론트엔드 서버의 주소
//        config.addAllowedOrigin("https://dslquant.site");
//        config.addAllowedOrigin("https://www.dslquant.site");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("authorization");
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
