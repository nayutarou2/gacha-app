package com.example.gacha_app_backend.exception;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.gacha_app_backend.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ ResourceNotFoundException.class, NotFoundException.class })
  public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorResponseDto error = new ErrorResponseDto(404, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler({ BadRequestException.class, MethodArgumentTypeMismatchException.class })
  public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex) {
    ErrorResponseDto error = new ErrorResponseDto(400, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // 3. その他、予期せぬエラー全てをキャッチ (500 Internal Server Error)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleAllOtherExceptions(Exception ex) {
    log.error("予期せぬエラーが発生しました", ex);
    ErrorResponseDto error = new ErrorResponseDto(500, "システムエラーが発生しました。");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
