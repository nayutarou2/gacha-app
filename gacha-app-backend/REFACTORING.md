gacha-app-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── gacha/       # 🌟 パッケージ名をシンプルに修正
│   │   │               ├── GachaApplication.java # クラス名もスッキリ変更可
│   │   │               │
│   │   │               ├── controller/   # Web APIの窓口 (Routing)
│   │   │               │   ├── GachaController.java
│   │   │               │   └── UserController.java
│   │   │               │
│   │   │               ├── service/      # ビジネスロジック
│   │   │               │   ├── AuthService.java
│   │   │               │   ├── GachaService.java
│   │   │               │   └── UserService.java
│   │   │               │
│   │   │               ├── repository/   # Mapperをラップする、またはデータアクセス層
│   │   │               │   ├── GachaMenuRepository.java
│   │   │               │   ├── GachaResultDetailRepository.java
│   │   │               │   ├── GachaResultRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               │
│   │   │               ├── mapper/       # MyBatisのMapperインターフェース
│   │   │               │   ├── GachaMenuMapper.java
│   │   │               │   ├── GachaResultDetailMapper.java
│   │   │               │   ├── GachaResultMapper.java
│   │   │               │   └── UserMapper.java
│   │   │               │
│   │   │               ├── entity/       # DBテーブルと1対1対応するモデル
│   │   │               │   ├── GachaMenu.java
│   │   │               │   ├── GachaResult.java
│   │   │               │   ├── GachaResultDetail.java
│   │   │               │   └── User.java
│   │   │               │
│   │   │               ├── dto/          # リクエスト/レスポンスのデータ構造
│   │   │               │   ├── ErrorResponseDto.java
│   │   │               │   ├── GachaDto.java
│   │   │               │   ├── GachaPullRequestDto.java
│   │   │               │   ├── GachaResultDto.java
│   │   │               │   ├── LoginRequestDto.java
│   │   │               │   ├── LoginResponseDto.java
│   │   │               │   └── UserDto.java
│   │   │               │
│   │   │               ├── security/     # 🌟 customフォルダをここに統合
│   │   │               │   ├── CustomUserDetail.java       # customから移動
│   │   │               │   ├── CustomUserDetailsService.java # customから移動
│   │   │               │   ├── JwtAuthenticationFilter.java
│   │   │               │   ├── JwtTokenProvider.java
│   │   │               │   └── SecurityConfig.java
│   │   │               │
│   │   │               ├── validation/   # バリデーション関連
│   │   │               │   ├── PasswordConstraintValidator.java
│   │   │               │   └── ValidPassword.java
│   │   │               │
│   │   │               └── exception/    # 例外ハンドリング
│   │   │                   ├── AuthenticationException.java
│   │   │                   ├── BadRequestException.java
│   │   │                   ├── GlobalExceptionHandler.java
│   │   │                   ├── ResourceNotFoundException.java
│   │   │                   └── ValidationException.java
│   │   │
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/        # Flywayのマイグレーションスクリプト
│   │       │       ├── h2/
│   │       │       └── postgresql/
│   │       ├── application.properties
│   │       └── passwords.txt
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── gacha/
│                       └── GachaApplicationTests.java
│
├── build.gradle
└── settings.gradle