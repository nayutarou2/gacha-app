package com.example.gacha.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GachaPullRequestDto {
  @NotNull(message = "ガチャの回数は必須です")
  private Integer kindNum;
}