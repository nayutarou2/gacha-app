package com.example.gacha_app_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gacha_app_backend.custom.CustomUserDetail;
import com.example.gacha_app_backend.dto.GachaDto;
import com.example.gacha_app_backend.dto.GachaPullRequestDto;
import com.example.gacha_app_backend.dto.GachaResultDto;
import com.example.gacha_app_backend.entity.GachaMenu;
import com.example.gacha_app_backend.entity.GachaResult;
import com.example.gacha_app_backend.service.GachaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gacha")
public class GachaController {

  private final GachaService gachaService;

  public GachaController(GachaService gachaService) {
    this.gachaService = gachaService;
  }

  @GetMapping("/pull")
  public ResponseEntity<List<GachaMenu>> allKinds() {

    System.out.println("ガチャの種類取得する処理");
    List<GachaMenu> gachaMenus = gachaService.selectAllKinds();
    System.out.println("ガチャの種類を返す" + gachaMenus);
    return ResponseEntity.ok(gachaMenus);

  }

  // ガチャを引く
  // ガチャの結果を保存する
  @PostMapping("/pull")
  // 何回引くかをユーザから取得
  // 配列でそれぞれの値に対する数を格納 例) [3(S),2(A),4(B),1(C)]
  public ResponseEntity<GachaDto> pullGacha(@RequestBody @Valid GachaPullRequestDto request,
      @AuthenticationPrincipal CustomUserDetail currentUser) {
    System.out.println("ガチャをまわす");
    // int kindNum = kindNums[0];
    int kindNum = request.getKindNum();
    // ガチャを回した結果を返す
    String[] gachaResult = gachaService.pullGacha(kindNum);
    // ガチャの詳細結果
    int[] gachaResultDetail = gachaService.resultCount(gachaResult);
    // ガチャの結果を登録する
    GachaResult result = gachaService.insert(kindNum, gachaResultDetail, gachaResult, currentUser);
    // 格納したidを取得
    Long id = result.getId();

    // 二つのものを格納して返す
    return ResponseEntity.ok(gachaService.responseBody(gachaResult, gachaResultDetail, id));

  }

  // ガチャの総回数を取得する
  @GetMapping("/result")
  // のちのちuserIdを取得する形に変更
  public ResponseEntity<Long> getAllResult(@AuthenticationPrincipal CustomUserDetail currentUser) {
    Long resposeBody = gachaService.selectAllGachaResult(currentUser.getId());
    return ResponseEntity.ok(resposeBody);
  }

  // 指定したidのガチャのデーsrc/main/java/com/example/gacha_app_backend/customタを取得
  @GetMapping("/result/{id}")
  public ResponseEntity<GachaResultDto> viewGachaResult(@PathVariable("id") Long id) {

    System.out.println("id" + id);

    GachaResultDto response = gachaService.selectByResultId(id);

    System.out.println("ガチャの結果を返す" + response);

    return ResponseEntity.ok(response);
  }

}
