package com.example.gacha_app_backend.repository;

import org.springframework.stereotype.Repository;

import com.example.gacha_app_backend.entity.GachaResult;
import com.example.gacha_app_backend.mapper.GachaResultMapper;

@Repository
public class GachaResultRepository {

  private final GachaResultMapper gachaResultMapper;

  public GachaResultRepository(GachaResultMapper gachaResultMapper) {
    this.gachaResultMapper = gachaResultMapper;
  }

  // 登録
  public int insertResult(GachaResult gachaResult) {
    return gachaResultMapper.insertResult(gachaResult);
  }

  // 指定したユーザの総回数全取得
  public Long selectAllGachaResult(Long userId) {
    return gachaResultMapper.selectAllGachaResult(userId);
  }

  // 指定したidのガチャのデータを取得
  public GachaResult selectById(Long id) {

    if (id == null) {
      throw new Error("指定されたidが見つかりませんでした。");
    }

    return gachaResultMapper.selectById(id);
  }

}
