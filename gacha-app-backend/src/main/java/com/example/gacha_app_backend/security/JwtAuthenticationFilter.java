package com.example.gacha_app_backend.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.gacha_app_backend.custom.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider tokenProvider;
  private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    try {
      // 1. リクエストのヘッダーからJWTトークンを取り出す
      String jwt = getJwtFromRequest(request);

      // 2. トークンが正しければ、中身を解析する
      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
        // トークンからemailを取得
        String email = tokenProvider.getEmailFromToken(jwt);

        // DBからユーザー情報を取得（CustomUserDetailsが返ってくる）
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 3. Spring Securityの世界での「認証済みVIPパス」を作成
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 4. 🌟【超重要】作成したパスをSpringのコンテキスト（ポケット）にしまう
        // これをすることで、コントローラーで @AuthenticationPrincipal が使えるようになります！
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (UsernameNotFoundException ex) {
      // エラーが起きてもここではアプリを止めず、ログだけ吐く
      logger.error("ユーザー認証を設定できませんでした", ex);
    }

    // 5. 次のフィルター（またはコントローラー）に処理をパスする
    filterChain.doFilter(request, response);
  }

  /**
   * ヘッダー（Authorization: Bearer トークン）からトークン部分だけを抜き出す便利メソッド
   */
  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7); // "Bearer " の後ろの文字列（トークン本体）だけを返す
    }
    return null;
  }
}