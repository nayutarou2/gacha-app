import axios from 'axios';
import type { AxiosInstance } from 'axios';
// import { AxiosError, InternalAxiosRequestConfig, AxiosResponse } from 'axios';

const api: AxiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials:true,
  // timeout: 5000,
});

api.interceptors.request.use((config) => {
  console.log('通信を行う :', config.url);
  return config;
});

export default api;
