"use client"

import { useActionState } from "react";
import { LoginAction, loginAction } from "@/app/api/auth";
import styles from "@/app/auth/login/login.module.css";
import Link from "next/link";

export default function LoginForm() {

  const initialState: LoginAction = {
    success: false,
    email: ""
  }

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
  const [state, formAction, isPending] = useActionState(loginAction, initialState);

  return (

    <form action={formAction} className={styles.form} >
      {state.error && <p className={styles.error_message}>{state.error}</p>}
      <div className={styles.form_section}>
        <label htmlFor="email" >メールアドレス</label>
        <input type="email" name="email" id="email" className={styles.input_login} defaultValue={state?.email} required />
      </div>
      <div className={styles.form_section}>
        <label htmlFor="password" >パスワード</label>
        <input type="password" name="password" id="password" className={styles.input_login} required />
      </div>
      <div className={styles.button_section}>
        <button type="submit" className={styles.button} disabled={isPending} >{isPending ? "送信中" : "ログイン"}</button>
      </div>
      <div className={styles.link_section}>
        <Link className={styles.register_link} href={"/auth/register"}>アカウント登録はこちら</Link>
      </div>
    </form>
  );

}
