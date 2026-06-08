package com.example.gacha_app_backend.validation;

import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RepeatCharacterRegexRule;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null)
      return false;

    PasswordValidator validator = new PasswordValidator(
        new Rule[] {
            // 1. 長さは8文字以上、30文字以下
            new LengthRule(8, 30),

            // 2. 文字種のルール（以下のうち、3つ以上を満たすこと、といった設定も可能）
            new CharacterRule(EnglishCharacterData.UpperCase, 1), // 大文字1文字以上
            new CharacterRule(EnglishCharacterData.LowerCase, 1), // 小文字1文字以上
            new CharacterRule(EnglishCharacterData.Digit, 1), // 数字1文字以上
            new CharacterRule(EnglishCharacterData.Special, 1), // 記号1文字以上

            // 3. 同じ文字が3回以上連続するのを禁止（例: aaaa や 1111 を弾く）
            new RepeatCharacterRegexRule(3),

            // 4. キーボードの並びや連番などの配列を禁止（例: 12345 や qwerty を弾く）
            new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, true), // アルファベット連番（例: abc）
            new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, true), // 数字の連番（例: 123）
            new IllegalSequenceRule(EnglishSequenceData.USQwerty, 3, true), // キーボードの並び（例: qwe）

            // 5. スペース（空白）の混入を禁止
            new WhitespaceRule()
        });

    RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid()) {
      return true;
    }

    // カスタムエラーメッセージのエミット（デフォルトのエラー文をPassayのものに差し替える）
    context.disableDefaultConstraintViolation();
    List<String> messages = validator.getMessages(result);
    String combinedMessage = String.join(", ", messages);
    context.buildConstraintViolationWithTemplate(combinedMessage).addConstraintViolation();

    return false;
  }
}