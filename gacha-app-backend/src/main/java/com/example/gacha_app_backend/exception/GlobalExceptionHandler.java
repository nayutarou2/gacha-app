package com.example.gacha_app_backend.exception;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ ResourceNotFoundException.class, NotFoundException.class })
  public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler({ BadRequestException.class, BadRequestException.class })
  public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  // 3. その他、予期せぬエラー全てをキャッチ (500 Internal Server Error)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllOtherExceptions(Exception ex) {
    System.out.println("404,400以外のエラー" + ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("システムエラーが発生しました。");
  }
}
