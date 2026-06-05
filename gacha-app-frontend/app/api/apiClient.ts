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

/** 
interface FailedRequest {
  resolve: (token: string) => void;
  reject: (error: AxiosError) => void;
}

let isRefreshing = false;
let failedQueue: FailedRequest[] = []; // 型安全に

const processQueue = (error: AxiosError | null, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else if (token) {
      prom.resolve(token);
    }
  });
  failedQueue = [];
};

// リクエストインターセプター
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // Cookie（HttpOnly）を使う場合、ブラウザが自動でTokenを送るため、
    // フロント側でここにわざわざAuthorizationヘッダーを手動で付ける処理は不要になります！
    return config;
  },
  (error: AxiosError) => Promise.reject(error)
);

// レスポンスインターセプター
api.interceptors.response.use(
  (response: AxiosResponse) => response,
  async (error: AxiosError) => {
    const originalRequest = error.config as InternalAxiosRequestConfig & { _retry?: boolean };

    if (error.response?.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        return new Promise<string>((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            if (originalRequest.headers) {
              originalRequest.headers.Authorization = `Bearer ${token}`;
            }
            return api(originalRequest);
          })
          .catch((err: AxiosError) => Promise.reject(err));
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        // バックエンド側がHttpOnly Cookieでリフレッシュトークンを管理している場合、
        // 引数にトークンを渡さなくても、Cookieが自動送信されます
        const response = await axios.post<{ accessToken: string }>(
          `${api.defaults.baseURL}/auth/refresh`,
          {},
          { withCredentials: true }
        );

        const { accessToken } = response.data;
        isRefreshing = false;
        processQueue(null, accessToken);

        return api(originalRequest);
      } catch (refreshError) {
        isRefreshing = false;
        // 型を安全に絞り込んで処理
        if (axios.isAxiosError(refreshError)) {
          processQueue(refreshError, null);
        }
        
        if (typeof window !== 'undefined') {
          window.location.href = '/login';
        }
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);
*/
export default api;
