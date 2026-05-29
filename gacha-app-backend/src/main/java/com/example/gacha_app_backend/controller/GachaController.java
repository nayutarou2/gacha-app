package com.example.gacha_app_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gacha_app_backend.dto.GachaDto;
import com.example.gacha_app_backend.entity.GachaMenu;
import com.example.gacha_app_backend.service.GachaService;


@Controller
@RestController
@RequestMapping("/api/gacha")
public class GachaController {

  private final GachaService gachaService;

  public GachaController(GachaService gachaService) {
    this.gachaService = gachaService;
  }

  @GetMapping("/pull")
  public ResponseEntity<List<GachaMenu>> allKinds() {

    List<GachaMenu> gachaMenus = gachaService.selectAllKinds();

    return ResponseEntity.ok(gachaMenus);

  }

  // ガチャを引く
  // ガチャの結果を保存する
  @PostMapping("/pull")
  // 何回引くかをユーザから取得
  // 配列でそれぞれの値に対する数を格納 例) [3(S),2(A),4(B),1(C)]
  public ResponseEntity<GachaDto> pullGacha(@RequestBody Map<String,Integer> request) {
    // int kindNum = kindNums[0];
    int kindNum = request.get("kindNum");
    // ガチャを回した結果を返す
    String[] gachaResult = gachaService.pullGacha(kindNum);
    // ガチャの詳細結果
    int[] gachaResultDetial = gachaService.resultCount(kindNum);
    // ガチャの結果を登録する
    gachaService.insert(kindNum);
    // 二つのものを格納して返す
    return ResponseEntity.ok(gachaService.responseBody(gachaResult, gachaResultDetial));

  }

  // ガチャの総回数を取得する
  @GetMapping("/result")
  // のちのちuserIdを取得する形に変更
  public ResponseEntity<Long> getAllResult() {
    Long resposeBody = gachaService.selectAllGachaResult((long) 1);
    return ResponseEntity.ok(resposeBody);
  }

}
