package com.example.gacha_app_backend.repository;

import org.springframework.stereotype.Repository;

import com.example.gacha_app_backend.entity.User;
import com.example.gacha_app_backend.mapper.UserMapper;

@Repository
public class UserRepository {

  private final UserMapper userMapper;

  public UserRepository(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  // 検索
  public User selectById(Long id) {
    return userMapper.selectById(id);
  }

  public Long findByUserId(Long id){
    return userMapper.findByUserId(id);
  }

  public boolean existsByEmail(String email){
    return userMapper.existsByEmail(email);
  }

  public User selectByEmail(String email){
    return userMapper.selectByEmail(email);
  }

  // 登録
  public int insertUser(User user) {
    return userMapper.insert(user);
  }

}
