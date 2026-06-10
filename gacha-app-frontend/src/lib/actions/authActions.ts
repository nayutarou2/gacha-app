'use server';

import axios from 'axios';
import api from '@/src/lib/api/apiClient';
import { createSession } from './sessionActions';
import { redirect } from 'next/navigation';
import { cookies } from 'next/headers';

// 成功か失敗かを返すprevStateに
export type LoginAction = {
  success?: boolean;
  email?: string;
  error?: string;
};

// バックエンドから返ってくるバリデーションエラーJSONの構造を定義
type BackendValidationError = {
  status: number;
  message: string;
  error: {
    username?: string[];
    email?: string[];
    password?: string[];
  };
};

export type RegisterAction = {
  success?: boolean;
  username?: string;
  email?: string;
  error?: string;
  fieldErrors?: {
    username?: string[];
    email?: string[];
    password?: string[];
  };
};

// reducerの実行
// reducer 左が変化前、右が変化後
export const loginAction = async (
  prevState: LoginAction | null,
  formData: FormData,
): Promise<LoginAction> => {
  //  emailとpasswordを取得
  const email = formData.get('email') as string;
  const password = formData.get('password') as string;

  try {
    const response = await api.post('/auth/login', { email, password });
    await createSession(response.data.token);
  } catch (error: unknown) {
    // エラーが起きたら、画面に表示するための文言を返す
    if (axios.isAxiosError(error)) {
      return {
        success: false,
        error: error.response?.data?.message || 'メールアドレスまたはパスワードが違います。',
      };
    }
    return {
      success: false,
      error: '通信エラーが発生しました。時間を置いて再度お試しください。',
    };
  }
  redirect('/gacha');
};

export const registerAction = async (
  prevState: RegisterAction | null,
  formData: FormData,
): Promise<RegisterAction> => {
  // username,email,password,passwordConfirmを取得
  const username = formData.get('username') as string;
  const email = formData.get('email') as string;
  const password = formData.get('password') as string;
  const passwordConfirm = formData.get('password_confirm') as string;

  // パスワードの一致チェック（フロント側でも簡易的にやる場合）
  if (password !== passwordConfirm) {
    return {
      success: false,
      username,
      email,
      fieldErrors: {
        password: ['パスワード(確認)が一致しません'],
        username: [],
      },
    };
  }

  try {
    await api.post('/auth/register', { username, email, password, passwordConfirm });
  } catch (error) {
    if (axios.isAxiosError<BackendValidationError>(error) && error.response) {
      const backendData = error.response.data;

      return {
        success: false,
        username,
        email,
        error: backendData.message,
        fieldErrors: backendData.error,
      };
    } else {
      return {
        success: false,
        username,
        email,
        error: '通信エラーが発生しました。',
      };
    }
  }
  redirect('/auth/login?success=true');
};

export async function logoutAction() {
  const cookieStore = await cookies();

  cookieStore.delete('jwt_token');

  redirect('/auth/login');
}
