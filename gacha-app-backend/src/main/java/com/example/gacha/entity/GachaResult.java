package com.example.gacha_app_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GachaResult {
  
  private Long id;
  private int sCount;
  private int aCount;
  private int bCount;
  private int cCount;
  private Long gachaMenuId;
  private Long userId;
  private LocalDateTime createdAt;

}
