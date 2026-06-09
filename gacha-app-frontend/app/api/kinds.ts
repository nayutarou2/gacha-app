"use server"

import api from './apiClient';
import { cookies } from 'next/headers'

type ApiError = {
  message: string;
  code: number;
};

export const allKinds = async () => {

  const cookieStore = await cookies();
  const token = cookieStore.get('jwt_token')?.value;

  try {
    const response = await api.get('/gacha/pull', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw error
  }
};
