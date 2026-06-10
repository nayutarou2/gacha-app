package com.example.gacha_app_backend.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.gacha_app_backend.entity.GachaResultDetail;
import com.example.gacha_app_backend.mapper.GachaResultDetailMapper;

@Repository
public class GachaResultDetailRepository {

  private final GachaResultDetailMapper gachaResultDetailMapper;

  public GachaResultDetailRepository(GachaResultDetailMapper gachaResultDetailMapper) {
    this.gachaResultDetailMapper = gachaResultDetailMapper;
  }

  // 検索
  public List<GachaResultDetail> selectDetailsByResultId(Long gachaResultId) {
    return gachaResultDetailMapper.selectDetailsByResultId(gachaResultId);
  }

  // 登録
  public int insertGachaResultDetail(GachaResultDetail gachaResultDetail) {
    return gachaResultDetailMapper.insert(gachaResultDetail);
  }

}
