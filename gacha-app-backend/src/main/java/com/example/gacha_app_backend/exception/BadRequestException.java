package com.example.gacha_app_backend.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(int num) {
    super(String.format("引数が不正です。%d",num));
  }
}
