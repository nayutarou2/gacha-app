package com.example.gacha.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.gacha.dto.LoginRequestDto;
import com.example.gacha.dto.LoginResponseDto;
import com.example.gacha.entity.User;
import com.example.gacha.exception.AuthenticationException;
import com.example.gacha.repository.UserRepository;
import com.example.gacha.security.JwtTokenProvider;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

  public LoginResponseDto login(LoginRequestDto request) {

    // 認証チェック emailとpasswordがあっているかの確認
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()));

    // 認証成功したのでJwtTokenを生成
    String token = tokenProvider.generateToken(authentication);

    User user = userRepository.selectByEmail(request.getEmail());

    // 画面にユーザ情報を表示したいのでDbから取得
    if (user == null) {
      throw new AuthenticationException("メールアドレスまたはパスワードが正しくありません");
    }

    // Controllerに返すためのResponseDtoを組み立てて返す
    return new LoginResponseDto(token, user.getId(), user.getEmail(), user.getUsername());

  }

}
