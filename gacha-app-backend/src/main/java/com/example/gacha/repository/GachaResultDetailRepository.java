package com.example.gacha.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.gacha.entity.GachaResultDetail;
import com.example.gacha.mapper.GachaResultDetailMapper;

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
