package com.example.gacha_app_backend.exception;

public class AuthenticationException extends RuntimeException {

  // ログイン失敗時のエラー
  public AuthenticationException(String message) {
    super(message);
  }
}