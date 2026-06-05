import axios from 'axios';
import api from './apiClient';

type ApiError = {
  message: string;
  code: number;
};

export const pullGacha = async (num: number, token: string) => {
  console.log("ガチャを引く");
  console.log("token", token);
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
