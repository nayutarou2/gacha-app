package com.example.gacha_app_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gacha_app_backend.dto.LoginRequestDto;
import com.example.gacha_app_backend.dto.LoginResponseDto;
import com.example.gacha_app_backend.dto.UserDto;
import com.example.gacha_app_backend.service.AuthService;
import com.example.gacha_app_backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

  private final UserService userService;
  private final AuthService authService;

  public UserController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  // user登録
  @PostMapping("/register")
  public ResponseEntity<Integer> createUser(UserDto userDto) {

    return ResponseEntity.ok(userService.insertUser(userDto));

  }

  // JWT構成のログインAPI
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {

    LoginResponseDto response = authService.login(request);

    return ResponseEntity.ok(response);

  }

}
