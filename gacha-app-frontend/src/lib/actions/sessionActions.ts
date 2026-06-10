import 'server-only';
import { cookies } from 'next/headers';

export async function createSession(token: string) {
  // const expiresAt = new Date(Date.now() + 7 * 24 * 60 * 60 * 1000)
  const cookieStore = await cookies();

  cookieStore.set('jwt_token', token, {
    httpOnly: true,
    secure: process.env.NODE_ENV === 'production', // 本番(https)ならtrue
    sameSite: 'lax',
    path: '/',
    maxAge: 60 * 60 * 24, // 1日有効（公式のDate型じゃなく、秒数で指定する方が簡単でおすすめ！）
  });
}
