package com.dashboard.dynamic_dashboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // غیرفعال کردن CSRF برای راحتی در تست APIها (در صورت نیاز بعداً شخصی‌سازی میشه)
                .csrf(AbstractHttpConfigurer::disable)
                // فعال‌سازی CORS با استفاده از CorsConfigurationSource که توی CorsConfig نوشتی
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        // اجازه عبور به درخواست‌های پیش‌پرواز (Preflight) مرورگر
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // موقتاً همه درخواست‌های API و داشبورد باز باشن تا فرانت‌اند بالا بیاد و گیر نکنه
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
