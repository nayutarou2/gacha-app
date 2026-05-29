# GachaApp

## 概要
ガチャを引くことができるアプリです。  
1,10,100の中から何連引くことができるか選択できます。


## 使用技術

### Frontend
* **Framework:** Next.js (v16)
* **Library:** React (v19)
* **Language:** TypeScript (v5)
* **HTTP Client:** Axios
* **Code Quality:** ESLint, Prettier

### Backend
* **Language:** Java 21
* **Framework:** Spring Boot (v3.5)
* **O/R Mapper:** MyBatis
* **Build Tool:** Gradle
* **Utilities:** Lombok

### Database
* **Database:** PostgreSQL
* **Migration Tool:** Flyway
* **Testing:** H2 Database (In-memory), JUnit 5


## インストール (Installation)
ソフトウェアをインストールし、環境を構築する手順をステップバイステップで記載します。
```bash
# リポジトリのクローン
$ git clone [https://github.com/nayutarou2/gacha-app.git](https://github.com/nayutarou2/gacha-app.git)

# backendディレクトリへの移動
$ cd gacha-app-backend

# frontendディレクトリ
$ cd gacha-app-frontend

# 依存パッケージのインストール
$ cd gacha-app-frontend
$ pnpm install
