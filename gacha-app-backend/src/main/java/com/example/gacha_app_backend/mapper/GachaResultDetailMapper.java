package com.example.gacha_app_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.gacha_app_backend.dto.GachaResultDto;
import com.example.gacha_app_backend.entity.GachaResultDetail;

@Mapper
public interface GachaResultDetailMapper {

  // gachaResultIdを指定してガチャの結果を取得
  @Select("""
      SELECT d.turns,d.rank,r.scount,r.acount,r.bcount,r.ccount FROM gacha_result_details d
      JOIN gacha_results r ON d.gacha_result_id = r.id
      WHERE d.gacha_result_id = #{gachaResultId}
      """)
  GachaResultDto selectByResultId(Long gachaResultId);

  // 登録
  @Insert("""
      INSERT INTO gacha_result_details (
      gacha_result_id,
      turns,
      rank
      ) VALUES (
      #{gachaResultId},
      #{turns},
      #{rank}
      )
      """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(GachaResultDetail gachaResultDetail);

}
