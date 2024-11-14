package com.myspringweb.amu_back.global.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = Logger.getLogger(WebConfig.class.getName());

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("CORS 연결 시작");
        registry.addMapping("/**").allowedOrigins("http://localhost:3000"); // React의 URL
    }
}
