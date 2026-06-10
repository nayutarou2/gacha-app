'use client';

import styles from '@/app/auth/login/login.module.css';
import Link from 'next/link';
import { useActionState } from 'react';

interface AuthState {
  success?: boolean;
  email?: string;
  username?: string; // ログイン時は使わないので「?（あってもなくてもいい）」にする
  error?: string; // エラー表示用
  fieldErrors?: {
    username?: string[];
    email?: string[];
    password?: string[];
  };
}

// ジェネリック型  を使って、ログイン・登録どちらの型が来ても対応できるようにします
interface AuthFormProps {
  auth: 'login' | 'register';
  action: (state: AuthState, formData: FormData) => Promise<AuthState> | AuthState;
  initialState: AuthState; // 初期値の型
}

export default function AuthForm(props: AuthFormProps) {
  /**
   * useActionState
   * 左辺
   * - 第一引数...状態(state)
   * - 第二引数...reducerを実行する関数
   * - 第三引数...reducerが進行中かどうかを知らせる状態
   * 右辺
   * - 第一引数...reducerを呼び出している関数
   * - 第二引数...初期値
   */
  const [state, formAction, isPending] = useActionState(props.action, props.initialState);
  const isLogin = props.auth == 'login';

  return (
    <>
      {state.error && <p className={styles.error_message}>{state.error}</p>}

      <form action={formAction} className={styles.form}>
        {!isLogin && (
          <div className={styles.form_section}>
            <label htmlFor="username">ユーザー名</label>
            <input
              type="text"
              name="username"
              id="username"
              className={styles.input_login}
              defaultValue={state.username}
              required
            />
            {state.fieldErrors?.username && (
              <p className={styles.field_error}>{state.fieldErrors.username.join('、')}</p>
            )}
          </div>
        )}

        <div className={styles.form_section}>
          <label htmlFor="email">メールアドレス</label>
          <input
            type="email"
            name="email"
            id="email"
            className={styles.input_login}
            defaultValue={state.email}
            required
          />
          {state.fieldErrors?.email && (
            <p className={styles.field_error}>{state.fieldErrors.email.join('、')}</p>
          )}
        </div>

        <div className={styles.form_section}>
          <label htmlFor="password">パスワード</label>
          <input
            type="password"
            name="password"
            id="password"
            className={styles.input_login}
            required
          />
          {state.fieldErrors?.password && (
            <p className={styles.field_error}>{state.fieldErrors.password.join('、')}</p>
          )}
        </div>

        {!isLogin && (
          <div className={styles.form_section}>
            <label htmlFor="passwordConf">パスワード（確認）</label>
            <input
              type="password"
              name="password_confirm"
              id="passwordConf"
              className={styles.input_login}
              required
            />
          </div>
        )}

        <div className={styles.button_section}>
          <button type="submit" className={styles.button} disabled={isPending}>
            {isPending ? (isLogin ? 'ログイン中...' : '登録中...') : isLogin ? 'ログイン' : '登録'}
          </button>
        </div>

        {isLogin ? (
          <div className={styles.link_section}>
            <Link className={styles.register_link} href={'/auth/register'}>
              アカウント登録はこちら
            </Link>
          </div>
        ) : (
          <div className={styles.link_section}>
            <Link className={styles.register_link} href={'/auth/login'}>
              アカウントをお持ちの方はこちら
            </Link>
          </div>
        )}
      </form>
    </>
  );
}
