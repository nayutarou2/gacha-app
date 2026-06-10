'use server';

import api from './apiClient';
import { cookies } from 'next/headers';

export const allKinds = async () => {
  const cookieStore = await cookies();
  const token = cookieStore.get('jwt_token')?.value;

  try {
    const response = await api.get('/gacha/pull', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};
