import axios from 'axios';
import api from './apiClient';

type ApiError = {
  message: string;
  code: number;
};

export const getAllResult = async (token:string) => {

  try {
    const response = await api.get('/gacha/result',{headers:{Authorization: `Bearer ${token}`}});
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

export const selectByResultId = async (id: number, token: string) => {
  try {
    const response = await api.get(`/gacha/result/${id}`, { headers: { Authorization: `Bearer ${token}` } });
    console.log(response.data);
    return response.data;
  } catch (error) {
    if (axios.isAxiosError<ApiError>(error)) {
      console.error(error.response?.data.message);
      console.error(error.response?.status);
      throw error;
    } else {
      throw error;
    }
  }
};
