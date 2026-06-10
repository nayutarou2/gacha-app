// middleware.ts
import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export function proxy(request: NextRequest) {
  // 1. クッキーからトークンを取得
  const token = request.cookies.get('jwt_token')?.value

  // 2. トークンが無い（未ログイン）状態で、保護されたページにアクセスしようとした場合
  if (!token) {
    // ログイン画面（/auth/login）へ強制リダイレクト
    return NextResponse.redirect(new URL('/auth/login', request.url))
  }

  // トークンがあれば、そのまま次の処理（ページ表示）へ進む
  return NextResponse.next()
}

// 🌟 ミドルウェアを実行する（＝認証チェックをかける）ルートを指定
export const config = {
  matcher: [
    '/',             // トップページ
    '/gacha/:path*', // /gacha や /gacha/result/123 など、gachaから始まるページすべて
  ],
}