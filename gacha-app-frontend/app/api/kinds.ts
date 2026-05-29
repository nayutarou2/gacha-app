import axios, { AxiosError } from 'axios';
import api from './apiClient';

type ApiError = {
  message: string;
  code: number;
};

export const allKinds = async () => {
  try {
    const response = await api.get('/pull');
    console.log(response.data);
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
