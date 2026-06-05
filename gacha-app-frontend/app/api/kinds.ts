import axios from 'axios';
import api from './apiClient';

type ApiError = {
  message: string;
  code: number;
};

export const allKinds = async (token: string) => {
  try {

    const response = await api.get('/gacha/pull',{
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log(response.data);
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
