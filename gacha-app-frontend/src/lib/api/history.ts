"use server"

import axios from 'axios';
import api from './apiClient';
import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';

type ApiError = {
  message: string;
  code: number;
};

export const getAllResult = async () => {

  const cookieStore = await cookies();
  const token = cookieStore.get('jwt_token')?.value;

  try {
    const response = await api.get('/gacha/result', { headers: { Authorization: `Bearer ${token}` } });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const selectByResultId = async (id: number) => {

  const cookieStore = await cookies();
  const token = cookieStore.get('jwt_token')?.value;

  try {
    const response = await api.get(`/gacha/result/${id}`, { headers: { Authorization: `Bearer ${token}` } });
    return response.data;
  } catch (error) {
    throw error
  }
};
