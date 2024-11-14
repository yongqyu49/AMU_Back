package com.myspringweb.amu_back.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CORS 설정
        http.cors(cors -> cors
                .configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true); // 세션 유지 허용
                    corsConfiguration.setMaxAge(3600L);
                    corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
                    return corsConfiguration;
                })
        );

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 폼 로그인 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        // HTTP 기본 인증 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 권한 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/signUp", "/user/signIn", "/user/current").permitAll()
                .anyRequest().authenticated()
        );

        // 세션 관리 설정
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 세션이 항상 생성되도록 설정
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
