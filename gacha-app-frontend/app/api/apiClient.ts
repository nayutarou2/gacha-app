import axios from 'axios';
import type { AxiosInstance } from 'axios';

const api: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api/gacha',
  // timeout: 5000,
});

api.interceptors.request.use((config) => {
  console.log('通信を行う :', config.url);
  return config;
});

export default api;
