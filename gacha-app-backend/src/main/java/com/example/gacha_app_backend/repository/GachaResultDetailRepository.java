package com.example.gacha_app_backend.repository;

import org.springframework.stereotype.Repository;

import com.example.gacha_app_backend.dto.GachaResultDto;
import com.example.gacha_app_backend.entity.GachaResultDetail;
import com.example.gacha_app_backend.mapper.GachaResultDetailMapper;

@Repository
public class GachaResultDetailRepository {

  private final GachaResultDetailMapper gachaResultDetailMapper;

  public GachaResultDetailRepository(GachaResultDetailMapper gachaResultDetailMapper) {
    this.gachaResultDetailMapper = gachaResultDetailMapper;
  }

  // 検索
  public GachaResultDto selectByResultId(Long id) {
    return gachaResultDetailMapper.selectByResultId(id);
  }

  // 登録
  public int insertGachaResultDetail(GachaResultDetail gachaResultDetail){
    return gachaResultDetailMapper.insert(gachaResultDetail);
  }


}
