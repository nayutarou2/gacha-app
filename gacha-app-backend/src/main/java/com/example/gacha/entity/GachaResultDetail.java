package com.example.gacha.entity;

import lombok.Data;

@Data
public class GachaResultDetail {
  
  private Long id;
  private Long gachaResultId;
  private int turns;
  private String rank;

}
