package com.example.gacha_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
  
  private int status;
  private String message;

}
