package com.myspringweb.amu_back.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                    corsConfiguration.setAllowCredentials(true);
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
                .requestMatchers(getExcludeUrls()).permitAll() // 인증이 필요하지 않은 경로
                .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한이 필요한 경로
                .anyRequest().authenticated() // 그 외의 요청은 인증 필요
        );

        // 최신 세션 관리 방식
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 기반 인증 사용
        );

        return http.build();
    }

    // exclude URLs를 반환하는 메서드 추가 (필요 시 구현)
    private String[] getExcludeUrls() {
        return new String[]{"/api/**"}; // 예시로 "/api/**" 경로를 인증 없이 접근 가능하게 설정
    }
}
