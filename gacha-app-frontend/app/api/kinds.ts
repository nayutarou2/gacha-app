"use server"

import axios from 'axios';
import api from './apiClient';
import { cookies } from 'next/headers'
import { redirect } from 'next/navigation';

type ApiError = {
  message: string;
  code: number;
};

export const allKinds = async () => {

  const cookieStore = await cookies();
  const token = cookieStore.get('jwt_token')?.value;

  try {
    const response = await api.get('/gacha/pull', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    if (axios.isAxiosError<ApiError>(error)) {
      // backendからの401レスポンス
      if (error.response?.status === 401) {
        return { success: false, error: 'auth_error' };
      }
      return { success: false, error: error.response?.data?.message || 'エラーが発生しました' }

    } else {
      return { success: false, error: "通信エラーが発生しました" }
    }
  }
};
