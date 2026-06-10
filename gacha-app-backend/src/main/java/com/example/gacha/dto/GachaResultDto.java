package com.example.gacha.dto;

import java.util.List;

import com.example.gacha.entity.GachaResultDetail;

import lombok.Data;

@Data
public class GachaResultDto {
  
  // ガチャの結果とガチャの詳細
  private int[] gachaResults;
  private List<GachaResultDetail> gachaResultDetails;

}
