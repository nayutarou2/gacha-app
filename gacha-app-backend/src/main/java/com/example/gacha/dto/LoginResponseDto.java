package com.example.gacha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;    // JWTトークン
    private Long id;         // ユーザーID
    private String email;    // メールアドレス
    private String username; // ニックネーム（もしDBにあって、画面に「ようこそ〇〇さん」と出したいなら残す！）
    
    // ❌ ここに「private String password;」は絶対に書いちゃダメ！
}
