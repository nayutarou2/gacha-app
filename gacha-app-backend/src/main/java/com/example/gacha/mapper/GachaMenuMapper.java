package com.example.gacha.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.gacha.entity.GachaMenu;

@Mapper
public interface GachaMenuMapper {


  // 登録してあるガチャの種類を全取得
  @Select("SELECT * FROM gacha_menus")
  List<GachaMenu> selectAllKinds();

  // ガチャの種類を取得
  @Select("SELECT * FROM gacha_menus WHERE kinds_num = #{kindsNum}")
  Long selectById(int kindsNum);

}
