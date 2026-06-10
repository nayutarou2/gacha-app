gacha-app-frontend/
├── .next/
├── node_modules/
├── public/
├── src/ # 🌟 ソースコードをここに集約
│ ├── app/ # 🌟 ルーティング専用のディレクトリにする
│ │ ├── auth/
│ │ │ ├── login/
│ │ │ │ ├── page.tsx
│ │ │ │ └── login.module.css
│ │ │ └── register/
│ │ │ ├── page.tsx
│ │ │ └── register.module.css
│ │ ├── gacha/
│ │ │ ├── result/
│ │ │ │ └── [id]/
│ │ │ │ ├── page.tsx
│ │ │ │ └── result.module.css
│ │ │ ├── gacha.module.css
│ │ │ └── page.tsx
│ │ ├── error.tsx
│ │ ├── favicon.ico
│ │ ├── globals.css
│ │ ├── layout.tsx
│ │ ├── not-found.tsx
│ │ └── page.tsx
│ │
│ ├── components/ # 🌟 関心事や機能ごとにフォルダを分ける
│ │ ├── ui/ # ボタンやタイトルなどの共通パーツ
│ │ │ ├── ClickBtn.tsx
│ │ │ ├── ClickBtn.module.css # タイポ(ClicBtn)を修正
│ │ │ └── Title.tsx
│ │ ├── layout/ # ヘッダーやフッターなど
│ │ │ ├── Header.tsx
│ │ │ └── Header.module.css
│ │ ├── auth/ # 認証関連のコンポーネント
│ │ │ ├── AuthForm.tsx
│ │ │ └── Logout.tsx
│ │ │ └── Logout.module.css
│ │ └── gacha/ # ガチャ関連のコンポーネント
│ │ ├── ChooseKind.tsx
│ │ ├── ChooseKind.module.css
│ │ ├── KindCard.tsx
│ │ ├── KindCard.module.css
│ │ ├── ResultAllCount.tsx
│ │ ├── ResultAllCount.module.css
│ │ ├── ResultCard.tsx
│ │ ├── ResultCard.module.css
│ │ ├── ResultDetail.tsx
│ │ └── ResultDetail.module.css
│ │
│ ├── lib/ # 🌟 外部API通信や共通ロジック（app/apiから移動）
│ │ ├── actions/ # Server Actions
│ │ │ ├── authActions.ts # auth.tsから改名
│ │ │ └── sessionActions.ts # actions.tsから改名
│ │ └── api/ # Axiosの設定やAPIクライアント関数
│ │ ├── apiClient.ts
│ │ ├── history.ts
│ │ ├── kinds.ts
│ │ └── pull.ts
│ │
│ ├── types/ # 🌟 型定義（app/interfaceから移動）
│ │ ├── kinds.ts # KindsData.tsから移動・改名
│ │ └── result.ts # ResultData.tsから移動・改名
│ │
│ └── middleware.ts # 🌟 proxy.tsから名前を変更してここに配置
│
├── .gitignore
├── .prettierrc
├── eslint.config.mjs
├── next.config.ts
├── package.json
├── pnpm-lock.yaml
├── pnpm-workspace.yaml
└── tsconfig.json
