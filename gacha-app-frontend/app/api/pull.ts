import axios, { AxiosError } from 'axios';
import api from './apiClient';

type ApiError = {
  message: string;
  code: number;
};

export const pullGacha = async (num:number) => {
  try {
    console.log(num);
    const response = await api.post('/pull',{
      kindNum:num
    });
    console.log("レスポンスデータ",response.data);
    return response.data;
  } catch (error) {
    if (axios.isAxiosError<ApiError>(error)) {
      // error.response?.data is typed as ApiError
      console.error(error.response?.data.message);
      console.error(error.response?.status);
    } else {
      throw error;
    }
  }
};
