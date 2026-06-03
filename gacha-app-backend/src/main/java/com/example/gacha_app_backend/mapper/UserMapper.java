package com.example.gacha_app_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.gacha_app_backend.entity.User;

@Mapper
public interface UserMapper {

  // user検索 引数id
  @Select("SELECT * FROM users WHERE id = #{id}")
  User selectById(Long id);

  // userId取得
  @Select("SELECT id FROM users WHERE id = #{id}")
  Long findByUserId(Long id);

  // もう登録してあるか email
  @Select("SELECT EXISTS(SELECT 1 FROM users WHERE email = #{email})")
  boolean existsByEmail(@Param("email") String email);

  // email検索
  @Select("SELECT * FROM users WHERE email = #{email}")
  User selectByEmail(String email);

  // user登録
  @Insert("""
      INSERT INTO users (
      username,email,password_hash
      ) VALUES (
      #{username},#{email},#{passwordHash}
      )
      """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(User user);

}
