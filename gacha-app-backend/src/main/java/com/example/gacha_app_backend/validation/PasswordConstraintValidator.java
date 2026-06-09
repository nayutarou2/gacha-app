package com.example.gacha_app_backend.validation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.passay.DictionaryRule; // 🌟「.dictionary.sort」を追加！
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RepeatCharacterRegexRule;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.passay.WhitespaceRule;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 🌟 @Slf4j (Lombokアノテーション)
 * クラス内に「log」という名前のロガーオブジェクトを自動生成する。
 * これにより、自前で定義を書くことなく log.warn() や log.error() が使えるようになる。
 */
@Slf4j
/**
 * 🌟 PasswordConstraintValidator (クラス本体)
 * ConstraintValidator インターフェースを実装することで、Springにカスタムバリデーターとして認識させる。
 * <ValidPassword, String> の意味：
 * - 第1引数: 自作した目印アノテーション「@ValidPassword」を対象にする。
 * - 第2引数: チェックする対象データの型は「String（文字列）」である。
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

  /**
   * 🌟 dictionaryRule (クラスのインスタンス変数 / フィールド)
   * initializeメソッドで読み込んだ「passwords.txt」の辞書ルールを一時的に保存しておく場所。
   * ここに保存しておくことで、後から動く isValid メソッドの中でも再利用できるようになる。
   */
  private DictionaryRule dictionaryRule;

  /**
   * 🌟 initialize (初期化メソッド)
   * アプリ起動後、このバリデーターが最初に使用されるタイミングで1度だけ実行される。
   * 主にファイルや設定の読み込みなど、重い前処理（事前準備）を行うために使用する。
   * * @param constraintAnnotation 貼られた @ValidPassword アノテーションのインスタンス（属性情報などを含む）
   */
  @Override
  public void initialize(ValidPassword constraintAnnotation) {

    /**
     * 🌟 try-with-resources 文
     * カッコ内 () で宣言したストリーム（is）は、tryブロックを抜けた瞬間に自動的に close() される。
     * これにより、ファイルの閉じ忘れによるメモリリーク（メモリの無駄遣い）を確実に防止する。
     * * 🌟 InputStream is (変数)
     * クラスローダーを使用して、JARファイルに圧縮された後も安全に resources 直下の「passwords.txt」を
     * バイトデータ（ストリーム）として読み込み、格納するための変数。
     */
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("passwords.txt")) {

      /**
       * 🌟 ファイル存在チェック
       * getResourceAsStream は、指定したファイル名が見つからない場合に「null」を返す仕様になっている。
       * nullのまま後続の処理に流すとバグるため、自ら明示的にファイル未検出エラー（例外）を発生させる。
       */
      if (is == null) {
        throw new FileNotFoundException("passwords.txt が resources 直下に見つかりません。");
      }

      /**
       * 🌟 WordListDictionary wordListDictionary (変数)
       * Passayライブラリが提供する、ブラックリスト単語の「目録（辞書）」オブジェクト。
       * * 🌟 WordLists.createFromReader(...) (静的メソッドの呼び出し)
       * 読み込んだ InputStream（バイトデータ）を InputStreamReader で文字データに変換し、配列の形で Passay に渡す。
       * 配列の型を「Reader[]」という抽象的な親クラスにすることで、FileReader等の型キャストの不整合を回避している。
       * - 第2引数(false): 大文字・小文字を区別しない（例: password も PASSWORD も両方同じ単語として弾く設定）。
       * - 第3引数(new ArraysSort()): 辞書内を高速検索できるように、単語をアルファベット順に内部ソートする設定。
       */
      WordListDictionary wordListDictionary = new WordListDictionary(
          WordLists.createFromReader(
              new Reader[] { new InputStreamReader(is) },
              false,
              new ArraysSort()));

      /**
       * 🌟 this.dictionaryRule (フィールドへの代入)
       * 完成した単語目録（wordListDictionary）を、Passayの「辞書拒否ルール」の形に変換し、
       * クラスのインスタンス変数（this.dictionaryRule）にしっかりと保存する。
       */
      this.dictionaryRule = new DictionaryRule(wordListDictionary);

      /**
       * 🌟 catch (FileNotFoundException e) (例外キャッチブロック)
       * ファイルが見つからなかった場合の個別ルート。親の IOException よりも必ず「先」に書くのがJavaの鉄則。
       * 
       * @param e 発生した FileNotFoundException のエラー情報が入っているオブジェクト
       */
    } catch (FileNotFoundException e) {

      // ファイルが無くてもアプリ自体は起動してほしいので、警告ログだけ吐いて辞書ルールをnull（スキップ）にする
      log.warn("【警告】パスワードの自作リスト（passwords.txt）が存在しないため、辞書チェックをスキップします。: {}", e.getMessage());
      this.dictionaryRule = null;

      /**
       * 🌟 catch (IOException e) (例外キャッチブロック)
       * ファイルはあるけれど、読み込みの途中でハードウェアトラブルや権限エラーが起きた場合のルート。
       * 
       * @param e 発生した演繹的な入出力エラー（IOException）の情報が入っているオブジェクト
       */
    } catch (IOException e) {
      // ファイルはあるけど、中身が壊れている・読み込み権限がないなどのエラー
      log.error("【エラー】パスワード自作リストの読み込み中に予期せぬエラーが発生しました。", e);
      this.dictionaryRule = null;
    }
  }

  /**
   * 🌟 isValid (バリデーション検証メソッド)
   * ユーザーが画面から登録ボタン等を押して、パスワードデータがバックエンドに届くたびに毎回実行される。
   * * @param password ユーザーがフォームに入力した「生のパスワード文字列」
   * 
   * @param context エラーメッセージのカスタマイズや上書きなど、検証結果の制御を行うためのオブジェクト
   * @return 検証結果（すべてのルールをクリアして合格なら true、1つでも不合格なら false）
   */
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {

    /**
     * 🌟 ヌル（未入力）チェック
     * パスワードがそもそも送られてきていない（null）場合は、これ以降の検査をする必要がないため、
     * 即座に不合格（false）を返して処理を終了させる。
     */
    if (password == null)
      return false;

    /**
     * 🌟 List<Rule> rules (変数)
     * これから実行するパスワードの検査ルール（Rule）を、一時的に詰め込んでおくための動的な「カゴ（リスト）」。
     */
    List<Rule> rules = new ArrayList<>();

    // NIST思想に基づき、文字種の強制をしない代わりに「12〜64文字」という長さをカゴに足す
    rules.add(new LengthRule(12, 64));
    // パスワードの中に「スペース（空白）」が紛れ込んでいないかチェックするルールをカゴに足す
    rules.add(new WhitespaceRule());
    // 「aaaa」や「1111」など、同じ文字が3回以上連続して並ぶのを禁止するルールをカゴに足す
    rules.add(new RepeatCharacterRegexRule(3));

    /**
     * 🌟 辞書ルールの合流
     * もし initialize メソッドの時点で「passwords.txt」が正常に読み込めていたら（nullじゃなければ）、
     * カゴ（rules）の中に、さっき作った「50選のブラックリスト単語ルール」も追加でドッキングさせる。
     */
    if (this.dictionaryRule != null) {
      rules.add(this.dictionaryRule);
    }

    /**
     * 🌟 PasswordValidator validator (変数)
     * カゴに入れたすべてのルールを実際に実行する、Passayのメイン「検品マシーン」オブジェクト。
     * * 🌟 rules.toArray(Rule[]::new) 【最重要ポイント】
     * PasswordValidator のコンストラクタは可変長引数（配列）を求めるため、List を配列に一発変換している。
     * Java 21 のコンパイラが型推論の迷子（List<T>はRule[]に適合しません）になるバグを、この明示的な配列化で完全に防いでいる。
     */
    PasswordValidator validator = new PasswordValidator(rules.toArray(Rule[]::new));

    /**
     * 🌟 RuleResult result (変数)
     * パスワード（password）を検証用データ（PasswordData）に包んで検品マシーンにかけ、
     * 返ってきた合否やエラー内容のすべてを格納した「検査結果レポート」オブジェクト。
     */
    RuleResult result = validator.validate(new PasswordData(password));

    /**
     * 🌟 合格判定
     * レポートの判定がすべてクリーン（合格）だった場合は、何も言わずに「true」を返す。
     * これにより、Controllerに処理が戻り、ユーザー登録処理が正常に次のフェーズへ進む。
     */
    if (result.isValid()) {
      return true;
    }

    /**
     * 🌟 デフォルトメッセージの破棄
     * アノテーション側（@ValidPassword）で設定されていた固定メッセージ「不適切なパスワードです」を消去する。
     * これを消すことで、以下で作成する「具体的な日本語の不合格理由」で上書きできるようになる。
     */
    context.disableDefaultConstraintViolation();

    /**
     * 🌟 List<String> errorMessages (変数)
     * 発生した複数の不合格理由を、きれいな日本語に翻訳して溜めておくための「メッセージ専用のカゴ」。
     */
    List<String> errorMessages = new ArrayList<>();

    /**
     * 🌟 エラー詳細のループ処理
     * result.getDetails() には、ダメだった理由（例：長さ不足、辞書に載っている、など）が複数入っている。
     * これを拡張for文を使って、1つずつの詳細（RuleResultDetail detail）として取り出して順番に処理する。
     */
    for (RuleResultDetail detail : result.getDetails()) {

      /**
       * 🌟 switch文（Java 14以降のラムダスタイル）
       * Passayから返ってきた英語の「エラーコード（detail.getErrorCode()）」をチェックし、
       * それに対応する最も分かりやすい「日本語のエラー文章」をカゴ（errorMessages）にどんどん追加していく。
       */
      switch (detail.getErrorCode()) {
        case "TOO_SHORT" -> errorMessages.add("パスワードは12文字以上で入力してください");
        case "ILLEGAL_WORD" -> errorMessages.add("簡単すぎる、またはよく使われるパスワードは指定できません");
        case "ILLEGAL_REPEATED_CHARS" -> errorMessages.add("同じ文字を連続して使用することはできません");
        case "ILLEGAL_WHITESPACE" -> errorMessages.add("パスワードに空白文字を含めることはできません");
        default -> errorMessages.add("セキュリティ基準を満たさないパスワードです");
      }
    }

    /**
     * 🌟 String combinedMessage (変数)
     * もし複数のエラーが同時に起きていた場合（例：10文字で、しかも空白入り）、
     * それらの文章を「。 」という文字で1つに綺麗に連結した、最終的なエラーテキスト。
     * （例：「〜入力してください。 〜含めることはできません」となる）
     */
    String combinedMessage = String.join("。 ", errorMessages);

    /**
     * 🌟 エラーメッセージの送信ドッキング
     * 完成した特製の日本語エラー文章（combinedMessage）を、Spring Validationのシステムへ
     * 「これをフロントエンド（Next.js）へのレスポンスとして送り返して！」と引き渡す処理。
     */
    context.buildConstraintViolationWithTemplate(combinedMessage).addConstraintViolation();

    /**
     * 🌟 不合格の確定通知
     * 最後に「false」を返して、バリデーションチェックを正式に「不合格」にする。
     * これによりController側で自動的にエラーが発生し、GlobalExceptionHandlerなどを通じてNext.jsに422エラーが返る。
     */
    return false;
  }
}