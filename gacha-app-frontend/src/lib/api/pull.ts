"use server"

import axios from 'axios';
import api from './apiClient';
import { cookies } from 'next/headers'

type ApiError = {
  message: string;
  code: number;
};

export const pullGacha = async (num: number) => {

  const cookieStore = await cookies()
  const token = cookieStore.get('jwt_token')?.value

  try {
    const response = await api.post('/gacha/pull',
      {
        kindNum: num,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
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
