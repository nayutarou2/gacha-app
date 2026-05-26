package com.example.gacha_app_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.gacha_app_backend.entity.GachaResult;

@Mapper
public interface GachaResultMapper {

  // ガチャの結果を登録
  @Insert("""
      INSERT INTO gacha_results (
      s_count,
      a_count,
      b_count,
      c_count,
      gacha_menu_id,
      user_id
      ) VALUES(
      #{sCount},
      #{aCount},
      #{bCount},
      #{cCount},
      #{gachaMenuId},
      1
      )
      """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insertResult(GachaResult gachaResult);

  // ガチャの総回数
  @Select("""
      SELECT COALESCE(SUM(m.kinds_num),0) FROM gacha_results r
      JOIN  gacha_menus m ON r.gacha_menu_id = m.id
      WHERE r.user_id = #{userId};
      """)
  Long selectAllGachaResult(Long userId);

}
