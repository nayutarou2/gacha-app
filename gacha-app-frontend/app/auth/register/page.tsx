"use client"

import Link from "next/link";
import styles from "./register.module.css";
import { registerAction, RegisterAction } from "@/app/api/auth";
import { useActionState } from "react";

const initialState: RegisterAction = {
  success: false,
  username: "",
  email: ""
};

export default function Register() {
  const [state, formAction, isPending] = useActionState(registerAction, initialState);

  return (
    <div className={styles.body}>
      <div className={styles.login}>
        <h1 className={styles.h1}>アカウント登録</h1>

        {/* エラーメッセージの表示 */}
        {state.error && <p className={styles.error_message}>{state.error}</p>}

        <form action={formAction} className={styles.form}>
          <div className={styles.form_section}>
            <label htmlFor="username">ユーザー名</label>
            <input type="text" name="username" id="username" className={styles.input_login} defaultValue={state.username} required />
          </div>

          <div className={styles.form_section}>
            <label htmlFor="email">メールアドレス</label>
            <input type="email" name="email" id="email" className={styles.input_login} defaultValue={state.email} required />
          </div>

          <div className={styles.form_section}>
            <label htmlFor="password">パスワード</label>
            <input type="password" name="password" id="password" className={styles.input_login} required />
          </div>

          <div className={styles.form_section}>
            <label htmlFor="passwordConf">パスワード（確認）</label>
            <input type="password" name="password_confirm" id="passwordConf" className={styles.input_login} required />
          </div>

          <div className={styles.button_section}>
            <button type="submit" className={styles.button} disabled={isPending}>
              {isPending ? "登録中..." : "登録"}
            </button>
          </div>

          <div className={styles.link_section}>
            <Link className={styles.register_link} href={"/auth/login"}>
              アカウントをお持ちの方はこちら
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}