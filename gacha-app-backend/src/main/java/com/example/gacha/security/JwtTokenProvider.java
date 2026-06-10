package com.example.gacha.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component // 🌟 これをつけることで、AuthServiceに@RequiredArgsConstructorでDIできるようになります！
public class JwtTokenProvider {

  // application.properties から設定値を読み込む（後述）
  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Value("${app.jwt.expiration-ms}")
  private long jwtExpirationMs;

  /**
   * 秘密鍵オブジェクトを生成する（0.12.x以降の安全なやり方）
   */
  private SecretKey getSigningKey() {
    byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * 必殺技①：ログイン成功時に「JWTトークン（文字列）」を発行する
   */
  public String generateToken(Authentication authentication) {
    // ログインに成功したユーザー情報（さっき作ったCustomUserDetailなど）を取得
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String email = userDetails.getUsername(); // 識別子（email）を取得

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

    // 🌟 最新0.12.x系は「set」が消えてすっきりしたメソッドチェーンになります
    return Jwts.builder()
        .subject(email) // トークンの主（email）をセット
        .issuedAt(now) // 発行時間をセット
        .expiration(expiryDate) // 有効期限をセット
        .signWith(getSigningKey(), Jwts.SIG.HS256) // 最新の署名アルゴリズム指定方法
        .compact(); // 文字列に変換して完成！
  }

  /**
   * 必殺技②：届いたトークンを解読して「email」を取り出す
   */
  public String getEmailFromToken(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(getSigningKey()) // 0.12.xの検証鍵セット方法
        .build()
        .parseSignedClaims(token) // トークンを解析
        .getPayload(); // 0.12.xではgetBody()ではなくgetPayload()

    return claims.getSubject(); // 暗号化されていたemailを返す
  }

  /**
   * 必殺技③：届いたトークンが「期限切れじゃないか」「改ざんされてないか」チェックする
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(getSigningKey())
          .build()
          .parseSignedClaims(token); // 解析に成功すれば、有効なトークン！
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      // 期限切れや改ざんがある場合は例外が発生するので、一律falseにする
      return false;
    }
  }
}