"use server"

import axios from 'axios';
import api from './apiClient';
import { createSession } from './actions';
import { redirect } from 'next/navigation';
import { cookies } from 'next/headers';

type ApiError = {
  message: string;
  code: number;
};

// 成功か失敗かを返すprevStateに
export type LoginAction = {
  success?: boolean;
  email?: string;
  error?: string;
};

export type RegisterAction = {
  success?: boolean;
  username?: string;
  email?: string;
  error?: string;
  fieldErrors?: {
    username: string[];
    email?: string[];
    password?: string[];
  };
}

// reducerの実行
// reducer 左が変化前、右が変化後
export const loginAction = async (prevState: LoginAction | null, formData: FormData): Promise<LoginAction> => {
  //  emailとpasswordを取得
  const email = formData.get("email") as string;
  const password = formData.get("password") as string;

  try {
    console.log("ログイン処理開始", email + "\n" + password);
    const response = await api.post("/auth/login", { email, password });
    console.log("ログインレスポンス:", response.data);
    await createSession(response.data.token);
  } catch (error: unknown) {
    console.error("ログインエラー:", error);
    // エラーが起きたら、画面に表示するための文言を返す
    if (axios.isAxiosError(error)) {
      return {
        success: false,
        error: error.response?.data?.message || "メールアドレスまたはパスワードが違います。"
      };
    }
    return {
      success: false,
      error: "通信エラーが発生しました。時間を置いて再度お試しください。"
    };
  }
  redirect('/gacha');
};

export const registerAction = async (prevState: RegisterAction | null, formData: FormData): Promise<RegisterAction> => {

  // username,email,password,passwordConfirmを取得
  const username = formData.get("username") as string;
  const email = formData.get("email") as string;
  const password = formData.get("password") as string;
  const passwordConfirm = formData.get("password_confirm") as string;

  // パスワードの一致チェック（フロント側でも簡易的にやる場合）
  if (password !== passwordConfirm) {
    return {
      success: false,
      username,
      email,
      error: "パスワードが一致しません"
    };
  }

  try {

    const response = await api.post("/auth/register", { username, email, password, passwordConfirm })

  } catch (error) {

    if (axios.isAxiosError<ApiError>(error)) {
      const errorMessage = error.response?.data.message || "登録に失敗しました";
      console.error(error.response?.data.message);
      console.error(error.response?.status);
      return { success: false, username: username, email: email, error: errorMessage }
    } else {
      throw error;
    }
  }
  redirect("/auth/login?success=true");
}

export async function logoutAction() {
  const cookieStore = await cookies();
  
  cookieStore.delete('jwt_token');
  
  redirect('/auth/login');
}