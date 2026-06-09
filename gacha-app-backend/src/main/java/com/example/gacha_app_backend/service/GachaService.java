package com.example.gacha_app_backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gacha_app_backend.custom.CustomUserDetail;
import com.example.gacha_app_backend.dto.GachaDto;
import com.example.gacha_app_backend.dto.GachaResultDto;
import com.example.gacha_app_backend.entity.GachaMenu;
import com.example.gacha_app_backend.entity.GachaResult;
import com.example.gacha_app_backend.entity.GachaResultDetail;
import com.example.gacha_app_backend.exception.BadRequestException;
import com.example.gacha_app_backend.exception.ResourceNotFoundException;
import com.example.gacha_app_backend.repository.GachaMenuRepository;
import com.example.gacha_app_backend.repository.GachaResultDetailRepository;
import com.example.gacha_app_backend.repository.GachaResultRepository;

@Service
public class GachaService {

  private final GachaMenuRepository gachaMenuRepository;
  private final GachaResultRepository gachaResultRepository;
  private final GachaResultDetailRepository gachaResultDetailRepository;
  private final UserService userService;

  private final int s = 5;
  private final int a = 15;
  private final int b = 30;
  // private final int c = 50;

  private final RandomGenerator generator = RandomGeneratorFactory.of("Xoshiro256PlusPlus").create();

  public GachaService(GachaMenuRepository gachaMenuRepository, GachaResultRepository gachaResultRepository,
      GachaResultDetailRepository gachaResultDetailRepository, UserService userService) {
    this.gachaMenuRepository = gachaMenuRepository;
    this.gachaResultRepository = gachaResultRepository;
    this.gachaResultDetailRepository = gachaResultDetailRepository;
    this.userService = userService;
  }

  // ガチャを指定された回数回すロジック
  @Transactional
  public String[] pullGacha(Integer kindNum) {

    if (kindNum <= 0) {
      throw new BadRequestException(kindNum);
    }

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
  public int[] resultCount(String[] result) {

    // String[] result = pullGacha(kindNum);

    int[] count = { 0, 0, 0, 0 };
    count[0] = (int) Arrays.stream(result).filter(f -> f.equals("S")).count();
    count[1] = (int) Arrays.stream(result).filter(f -> f.equals("A")).count();
    count[2] = (int) Arrays.stream(result).filter(f -> f.equals("B")).count();
    count[3] = (int) Arrays.stream(result).filter(f -> f.equals("C")).count();

    return count;

  }

  // 保存ロジック
  @Transactional
  public GachaResult insert(int kindNum, int[] detail, String[] gachaResults, CustomUserDetail currentUser) {

    if (gachaMenuRepository.selectById(kindNum) == null) {
      throw new BadRequestException(kindNum);
    }

    // ガチャの結果を保存
    GachaResult gachaResult = new GachaResult();
    // resultカウントを入れるは配列を作成
    gachaResult.setSCount(detail[0]);
    gachaResult.setACount(detail[1]);
    gachaResult.setBCount(detail[2]);
    gachaResult.setCCount(detail[3]);
    gachaResult.setGachaMenuId(gachaMenuRepository.selectById(kindNum));
    gachaResult.setUserId(userService.findByUserId(currentUser.getId()));

    try {
      gachaResultRepository.insertResult(gachaResult);

      Long gachaResultId = gachaResult.getId();
      // gachaResultDetailRepository.insertGachaResultDetail();
      // resultIdを参照して回数分繰り返す
      for (int i = 0; i < gachaResults.length; i++) {
        GachaResultDetail gachaResultDetail = new GachaResultDetail();
        gachaResultDetail.setGachaResultId(gachaResultId);
        gachaResultDetail.setTurns(i + 1);
        gachaResultDetail.setRank(gachaResults[i]);
        gachaResultDetailRepository.insertGachaResultDetail(gachaResultDetail);
      }

    } catch (Exception e) {
      throw new RuntimeException("登録できませんでした");
    }

    return gachaResult;
  }

  // ガチャを引いた結果とその詳細結果を返す
  @Transactional
  public GachaDto responseBody(String[] gachaResult, int[] gachaResultDetail, Long id) {

    GachaDto gachaDto = new GachaDto();
    gachaDto.setGachaResult(gachaResult);
    gachaDto.setGachaResultDetail(gachaResultDetail);
    gachaDto.setId(id);

    return gachaDto;
  }

  // 指定したユーザの総回数全取得
  @Transactional
  public Long selectAllGachaResult(Long userId) {
    if (userId == null) {
      throw new ResourceNotFoundException("総回数取得結果", userId);
    }
    return gachaResultRepository.selectAllGachaResult(userId);
  }

  // 種類全取得
  @Transactional
  public List<GachaMenu> selectAllKinds() {
    return gachaMenuRepository.selectAllKinds();
  }

  @Transactional
  public Long selectById(int kindsNum) {
    if (kindsNum <= 0) {
      throw new BadRequestException(kindsNum);
    }

    return gachaMenuRepository.selectById(kindsNum);
  }

  // 指定したgachaResultIdでガチャの結果を取得
  @Transactional
  public GachaResultDto selectByResultId(Long id) {
    if (id == null) {
      throw new ResourceNotFoundException("ガチャデータ取得結果", id);
    }

    GachaResult result = gachaResultRepository.selectById(id);

    if (result == null) {
      throw new ResourceNotFoundException("ガチャのデータが存在しません", id);
    }

    int[] resultCount = { result.getSCount(), result.getACount(), result.getBCount(), result.getCCount() };

    GachaResultDto gachaResultDto = new GachaResultDto();
    gachaResultDto.setGachaResults(resultCount);

    gachaResultDto.setGachaResultDetails(gachaResultDetailRepository.selectDetailsByResultId(id));

    return gachaResultDto;
  }

}
