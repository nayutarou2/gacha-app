# Gacha Application (gacha-app)
ユーザー登録・ログインから、様々な種類のガチャを引いて結果を記録・確認できるフルスタックのガチャアプリケーションです。

## 🛠 技術スタック

### バックエンド

- **言語・フレームワーク:** Java 17+ / Spring Boot
- **ビルドツール:** Gradle
- **セキュリティ:** Spring Security + JWT (JSON Web Token)
- **データベース:** PostgreSQL (本番・開発用) / H2 Database (インメモリ/テスト用)
- **DBマイグレーション:** Flyway

### フロントエンド 
- **フレームワーク:** Next.js (App Router / TypeScript)
- **パッケージマネージャー:** pnpm
- **スタイリング:** CSS Modules

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
* Node.js がインストールされていること
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
- 認証・認可機能: ユーザー登録、セキュアなJWTベースのログイン/ログアウト。
- ガチャメニュー選択: 異なる種類（Kinds）のガチャメニューから引きたいガチャを選択。
- ガチャ実行 & 結果表示: ガチャを引いて結果（詳細含む）を出力・保存。
- 履歴確認: 過去に引いたガチャの結果一覧や統計情報の確認。

