package com.example.gacha.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

  @NotBlank(message = "メールアドレスを入力してください")
  @Email(message = "メールアドレスの形式が正しくありません")
  private String email;
  @NotBlank(message = "パスワードを入力してください")
  private String password;

}
