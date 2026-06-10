# Gacha Application (gacha-app)
ユーザー登録・ログインから、様々な種類のガチャを引いて結果を記録・確認できるフルスタックのガチャアプリケーションです。

## 🛠 技術スタック

### フロントエンド 
- **Node.js:** v20.9.0+
- **フレームワーク:** Next.js v16.2.6 (App Router)
- **ライブラリ:** React v19.2.4
- **言語:** TypeScript v5.9.3
- **スタイリング:** CSS Modules
- **Package Manager:** pnpm v9.x
- **HTTP Client:** Axios v1.17.0
- **Code Quality:** ESLint v9.x, Prettier v3.8.3

### バックエンド

- **言語・フレームワーク:** Java 21+ / Spring Boot v3.5.14
- **O/R Mapper:** MyBatis v3.0.5
- **ビルドツール:** Gradle v8.14.4(Wrapper内蔵)
- **セキュリティ:** Spring Security + JWT (JSON Web Token) , Passay v1.6.6 (パスワードバリデーション)
- **ユーティリティ:** Lombok

### データベース
- **データベース:** PostgreSQL (本番・開発用) / H2 Database (インメモリ/テスト用)
- **DBマイグレーション:** Flyway

## 📁 ディレクトリ構造
```
gacha-app/
├── gacha-app-backend/     # バックエンド (Spring Boot)
│   ├── src/main/java/     # ソースコード (Controller, Service, Repository, DTO等)
│   └── src/main/resources/# 設定ファイル、DBマイグレーション(H2/PostgreSQL)
└── gacha-app-frontend/    # フロントエンド (Next.js)
    └── src/               # 画面コンポーネント、APIクライアント、各種ロジック
```
## 🚀 🚀 開発環境の構築・起動手順

最初にリポジトリをクローンしてください。
$git clone [https://github.com/nayutarou2/gacha-app.git$](https://github.com/nayutarou2/gacha-app.git$) cd gacha-app

### 1. バックエンドの起動 (`gacha-app-backend`) 
#### 前提条件  
- Java 17 以上がインストールされていること

#### 起動手順  
- バックエンドのディレクトリに移動します。

```
cd gacha-app-backend
```

2. アプリケーションをビルドおよび起動します。

**Mac / Linux:**
```
./gradlew bootRun
```
Windows:
```
gradlew.bat bootRun
```

> 💡 **補足:** データベースの設定は `src/main/resources/application.properties` および `db/migration` を確認してください。デフォルトではH2およびPostgreSQLのマイグレーションファイルが用意されています。

---

### 2. フロントエンドの起動 (`gacha-app-frontend`)

#### 前提条件
* Node.js(v20.9.0以上) がインストールされていること
* `pnpm` がインストールされていること (`npm i -g pnpm`)

#### 起動手順
1. フロントエンドのディレクトリに移動します。
```
cd gacha-app-frontend
```
2. 依存関係をインストールします。
```
pnpm install
```
3. 開発サーバーを起動します。
```
pnpm run dev
```
ブラウザで http://localhost:3000 にアクセスします。

## 📝 主な機能
1. セキュアな認証機能:
- パスウェイ（Passay）ライブラリを用いたセキュリティ基準を満たすパスワードバリデーション付きのユーザー登録。
- JWT（JSON Web Token）および Cookie（httpOnly）を利用したステートレスなログイン・ログアウト、および保護ルートのミドルウェア制御。

2. ガチャメニュー選択:
- データベースに登録されたガチャメニュー（1連、10連、100連）を動的に取得・表示。
- ガチャ実行・結果保存:

3. 確率に基づいたガチャの抽選（S / A / B / Cランク）。
- ガチャを回したサマリー（各ランクの排出回数）と、1回ごとの詳細（何回目にどのランクが出たか）をリレーショナルにデータベースへ保存。

4. ダッシュボード・履歴確認:
- これまでに全ユーザーが引いたガチャの総数をトップページに表示。
- 指定したガチャのIDから詳細な結果画面をいつでも確認可能。

