package com.example.gacha_app_backend.exception;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {
  // 登録時のバリデーションエラー
  private final Map<String, List<String>> errors;

  public ValidationException(Map<String, List<String>> errors, String message) {
    super(message);
    this.errors = errors;
  }

  public Map<String, List<String>> getErrors() {
    return errors;
  }
}