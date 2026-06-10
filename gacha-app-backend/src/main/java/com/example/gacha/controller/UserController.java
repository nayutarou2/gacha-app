package com.example.gacha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gacha.dto.LoginRequestDto;
import com.example.gacha.dto.LoginResponseDto;
import com.example.gacha.dto.UserDto;
import com.example.gacha.service.AuthService;
import com.example.gacha.service.UserService;

import jakarta.validation.Valid;

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
  public ResponseEntity<Integer> createUser(@RequestBody @Valid UserDto userDto) {

    return ResponseEntity.ok(userService.insertUser(userDto));

  }

  // JWT構成のログインAPI
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {

    LoginResponseDto response = authService.login(request);

    return ResponseEntity.ok(response);

  }

}
