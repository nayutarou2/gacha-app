import axios from 'axios';
import api from './apiClient';
import { cookies } from 'next/headers'
import { redirect } from 'next/navigation'

type ApiError = {
  message: string;
  code: number;
};

export const pullGacha = async (num: number) => {

  const cookieStore = await cookies()
  const token = cookieStore.get('jwt_token')?.value

  if (!token) {
    redirect('/auth/login')
  }


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
    console.log('レスポンスデータ', response.data);
    return response.data;
  } catch (error) {
    if (axios.isAxiosError<ApiError>(error)) {
      // error.response?.data is typed as ApiError
      console.error(error.response?.data.message);
      console.error(error.response?.status);
      throw error;
    } else {
      throw error;
    }
  }
};
