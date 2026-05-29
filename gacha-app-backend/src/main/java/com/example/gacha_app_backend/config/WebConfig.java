package com.example.gacha_app_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  // NonNullで空っぽのものは渡さないということ
  public void addCorsMappings(@NonNull CorsRegistry registry) {
    registry.addMapping("/api/gacha/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("GET", "POST")
        // .allowedHeaders("Content-Type")
        .allowedHeaders("*")
        // .allowCredentials(true)
        .maxAge(3600);
  }

}
