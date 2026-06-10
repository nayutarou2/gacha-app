package com.example.gacha.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.example.gacha.entity.GachaResultDetail;

@Mapper
public interface GachaResultDetailMapper {

  // gachaResultIdを指定してガチャの結果を取得
//   @Select("""
//       SELECT r.id AS parent_id, d.turns,d.rank,r.s_count,r.a_count,r.b_count,r.c_count FROM gacha_result_details d
//       JOIN gacha_results r ON d.gacha_result_id = r.id
//       WHERE d.gacha_result_id = #{gachaResultId}
//       """)
//   GachaResultDto selectByResultId(Long gachaResultId);
// ① 親をIDで1行だけ取る


    @Select("SELECT * FROM gacha_result_details WHERE gacha_result_id = #{gachaResultId}")
    List<GachaResultDetail> selectDetailsByResultId(Long gachaResultId);

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
