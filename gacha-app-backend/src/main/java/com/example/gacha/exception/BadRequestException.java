package com.example.gacha.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(int num) {
    super(String.format("引数が不正です。%d",num));
  }
}
