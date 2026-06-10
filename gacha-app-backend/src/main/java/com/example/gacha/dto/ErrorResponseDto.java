package com.example.gacha.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
// nullのフィールドはJSONに出力しない魔法の呪文
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
  
  private int status;
  private String message;
  private Map<String,List<String>> error;

}
