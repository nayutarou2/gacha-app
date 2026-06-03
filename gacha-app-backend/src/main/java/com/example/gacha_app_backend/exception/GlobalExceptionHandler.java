package com.example.gacha_app_backend.exception;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.gacha_app_backend.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 各Exceptionはmessages.propertiesに移行

  // 400
  @ExceptionHandler({ BadRequestException.class, MethodArgumentTypeMismatchException.class })
  public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex) {
    ErrorResponseDto error = new ErrorResponseDto(400, ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // 401
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponseDto> handleAuthentication(AuthenticationException ex) {
    ErrorResponseDto error = new ErrorResponseDto(401, ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  // 404
  @ExceptionHandler({ ResourceNotFoundException.class, NotFoundException.class })
  public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorResponseDto error = new ErrorResponseDto(404, ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  // 422
  @ExceptionHandler({ ValidationException.class, HttpClientErrorException.UnprocessableEntity.class })
  public ResponseEntity<ErrorResponseDto> handleValidation(ValidationException ex) {
    ErrorResponseDto error = new ErrorResponseDto(422, ex.getMessage(), ex.getErrors());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  // 予期せぬエラー全てをキャッチ (500 Internal Server Error)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleAllOtherExceptions(Exception ex) {
    log.error("予期せぬエラーが発生しました", ex);
    ErrorResponseDto error = new ErrorResponseDto(500, "システムエラーが発生しました。", null);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
