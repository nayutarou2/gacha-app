package com.example.gacha.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.gacha.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 各Exceptionはmessages.propertiesに移行

  // 400 引数が見つからない
  @ExceptionHandler({ BadRequestException.class })
  public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex) {
    ErrorResponseDto error = new ErrorResponseDto(400, ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // 400: URLのパスパラメータの型が違う場合（例: /result/abc などの文字が入ってきた時）
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    ErrorResponseDto error = new ErrorResponseDto(400, "リクエストパラメータの型が正しくありません。", null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // 401 認証失敗
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponseDto> handleAuthentication(AuthenticationException ex) {
    ErrorResponseDto error = new ErrorResponseDto(401, ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  // 404 ページが見つからない
  @ExceptionHandler({ ResourceNotFoundException.class, NotFoundException.class })
  public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorResponseDto error = new ErrorResponseDto(404, ex.getMessage(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  // 422 Service層でのバリデーションエラー
  @ExceptionHandler({ ValidationException.class })
  public ResponseEntity<ErrorResponseDto> handleValidation(ValidationException ex) {
    ErrorResponseDto error = new ErrorResponseDto(422, ex.getMessage(), ex.getErrors());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  // 422 @Validアノテーションによる入力チェックエラーのハンドリング
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    Map<String, List<String>> errors = new HashMap<>();

    // 発生したエラーを「フィールド名：メッセージのリスト」の形に整形する
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      String fieldName = fieldError.getField();
      String errorMessage = fieldError.getDefaultMessage();

      errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
    }

    // エラーレスポンスDtoを組み立てて返す（既存のErrorResponseDtoの構造に合わせる）
    ErrorResponseDto error = new ErrorResponseDto(422, "入力内容に不備があります。", errors);
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
