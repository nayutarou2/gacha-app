package com.example.gacha.exception;

public class AuthenticationException extends RuntimeException {

  // ログイン失敗時のエラー
  public AuthenticationException(String message) {
    super(message);
  }
}