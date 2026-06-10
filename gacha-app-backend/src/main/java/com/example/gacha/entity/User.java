package com.example.gacha_app_backend.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
  
  private Long id;
  private String username;
  private String email;
  private String passwordHash;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
