package com.example.gacha.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // 1. JWTを使うのでCSRFは一言で無効化
        .csrf(AbstractHttpConfigurer::disable)

        // 2. CORS設定（Next.jsからのアクセスを許可。別の場所に逃がしてスッキリさせる）
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        // 3. セッションは使わない（ステートレス）お守り
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin()))

        // 4. どのURLを「誰でも通していいか」の仕分け
        .authorizeHttpRequests(auth -> auth
            // ログイン、ユーザー登録、画像などは誰でもウェルカム
            .requestMatchers("/api/auth/**", "/images/**", "/error", "/h2-console/**").permitAll()
            // それ以外の「マイページ」などのAPIは要ログイン
            .anyRequest().authenticated());

    // 5. JWTをチェックするフィルター」を1行だけ足す
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    return encoder;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  // メインコードの下にこれを貼り付けておくだけ！
  private CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Next.jsを許可
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}