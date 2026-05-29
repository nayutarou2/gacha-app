package com.example.gacha_app_backend.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gacha_app_backend.dto.GachaDto;
import com.example.gacha_app_backend.entity.GachaMenu;
import com.example.gacha_app_backend.entity.GachaResult;
import com.example.gacha_app_backend.repository.GachaMenuRepository;
import com.example.gacha_app_backend.repository.GachaResultRepository;

@Service
public class GachaService {

  private final GachaMenuRepository gachaMenuRepository;
  private final GachaResultRepository gachaResultRepository;

  private final int s = 5;
  private final int a = 15;
  private final int b = 30;
  // private final int c = 50;

  private final RandomGenerator generator = RandomGeneratorFactory.of("Xoshiro256PlusPlus").create();

  public GachaService(GachaMenuRepository gachaMenuRepository, GachaResultRepository gachaResultRepository) {
    this.gachaMenuRepository = gachaMenuRepository;
    this.gachaResultRepository = gachaResultRepository;
  }

  // ガチャを指定された回数回すロジック
  @Transactional
  public String[] pullGacha(int kindNum) {

    String[] result = new String[kindNum];

    int num;

    int aProbality = s + a;
    int bProbality = s + a + b;

    for (int i = 0; i < kindNum; i++) {
      num = generator.nextInt(100);
      if (num < s) {
        result[i] = "S";
      } else if (num < aProbality) {
        result[i] = "A";
      } else if (num < bProbality) {
        result[i] = "B";
      } else {
        result[i] = "C";
      }
    }

    return result;

  }

  // 配列の中身を調べるもの
  @Transactional
  public int[] resultCount(int kindNum) {

    String[] result = pullGacha(kindNum);

    int[] count = { 0, 0, 0, 0 };
    count[0] = (int) Arrays.stream(result).filter(f -> f.equals("S")).count();
    count[1] = (int) Arrays.stream(result).filter(f -> f.equals("A")).count();
    count[2] = (int) Arrays.stream(result).filter(f -> f.equals("B")).count();
    count[3] = (int) Arrays.stream(result).filter(f -> f.equals("C")).count();

    return count;

  }

  // 保存ロジック
  @Transactional
  public GachaResult insert(int kindNum) {

    if (gachaMenuRepository.selectById(kindNum) == null) {
      throw new Error("null kinds num");
    }

    // ガチャの結果を保存
    GachaResult gachaResult = new GachaResult();
    // resultカウントを入れるは配列を作成
    int[] resultCount = resultCount(kindNum);
    gachaResult.setSCount(resultCount[0]);
    gachaResult.setACount(resultCount[1]);
    gachaResult.setBCount(resultCount[2]);
    gachaResult.setCCount(resultCount[3]);
    gachaResult.setCreatedAt(LocalDateTime.now());
    gachaResult.setGachaMenuId(gachaMenuRepository.selectById(kindNum));
    gachaResult.setUserId((long) 1);


    try {
      gachaResultRepository.insertResult(gachaResult);
    } catch (Exception e) {
      throw new Error("登録できませんでした");
    }


    return gachaResult;
  }

  // ガチャを引いた結果とその詳細結果を返す
  @Transactional
  public GachaDto responseBody(String[] gachaResult, int[] gachaResultDetial) {

    GachaDto gachaDto = new GachaDto();
    gachaDto.setGachaResult(gachaResult);
    gachaDto.setGachaResultDetail(gachaResultDetial);

    return gachaDto;
  }

  // 指定したユーザの総回数全取得
  @Transactional
  public Long selectAllGachaResult(Long userId) {
    return gachaResultRepository.selectAllGachaResult(userId);
  }

  // 種類全取得
  @Transactional
  public List<GachaMenu> selectAllKinds() {
    return gachaMenuRepository.selectAllKinds();
  }

  @Transactional
  public Long selectById(int kindsNum) {
    return gachaMenuRepository.selectById(kindsNum);
  }

}
