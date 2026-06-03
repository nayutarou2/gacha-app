package com.example.gacha_app_backend.dto;

import lombok.Data;

@Data
public class UserDto {
  
  private String username;
  private String email;
  private String password;
  private String passwordConfirm;

}
