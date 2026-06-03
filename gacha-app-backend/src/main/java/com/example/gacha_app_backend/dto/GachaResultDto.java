package com.example.gacha_app_backend.dto;

import java.util.List;

import com.example.gacha_app_backend.entity.GachaResult;
import com.example.gacha_app_backend.entity.GachaResultDetail;

import lombok.Data;

@Data
public class GachaResultDto {
  
  // ガチャの結果とガチャの詳細
  private GachaResult gachaResults;
  private List<GachaResultDetail> gachaResultDetails;

}
