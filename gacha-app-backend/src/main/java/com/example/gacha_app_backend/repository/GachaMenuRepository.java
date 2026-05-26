package com.example.gacha_app_backend.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.gacha_app_backend.entity.GachaMenu;
import com.example.gacha_app_backend.mapper.GachaMenuMapper;

@Repository
public class GachaMenuRepository {

  private final GachaMenuMapper gachaMenuMapper;

  public GachaMenuRepository(GachaMenuMapper gachaMenuMapper) {
    this.gachaMenuMapper = gachaMenuMapper;
  }

  // 種類全取得
  public List<GachaMenu> selectAllKinds(){
    return gachaMenuMapper.selectAllKinds();
  }  

  public Long selectById(int kindsNum){
    return gachaMenuMapper.selectById(kindsNum);    
  }

}
