package com.example.gacha_app_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gacha_app_backend.dto.UserDto;
import com.example.gacha_app_backend.entity.User;
import com.example.gacha_app_backend.exception.ValidationException;
import com.example.gacha_app_backend.repository.UserRepository;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // 検索
  public User selectById(Long id) {
    return userRepository.selectById(id);
  }

  //userId検索
  public Long findByUserId(Long id){
    return userRepository.findByUserId(id);
  }

  // 登録
  public int insertUser(UserDto userDto) {

    // パスワードチェック
    if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
      throw new ValidationException(
          Map.of("passwordConfirm", List.of("パスワードが一致しません")),
          "パスワードが一致しません");
    }

    if (userRepository.existsByEmail(userDto.getEmail())) {
      throw new ValidationException(
          Map.of("email", List.of("このメールアドレスは既に登録されています。")),
          "登録エラー");
    }

    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setEmail(userDto.getEmail());

    String hashedPassword = passwordEncoder.encode(userDto.getPassword());
    user.setPasswordHash(hashedPassword);

    return userRepository.insertUser(user);
  }

}
