import Header from '@/components/Header';
import ClickBtn from '@/components/ClickBtn';
import ResultAllCount from '@/components/ResultAllCount';
import { getAllResult } from './api/history';
import axios from 'axios';
import { redirect } from 'next/navigation';

export default async function Home() {

  let response;

  try {
    response = await getAllResult();
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 401) {
      redirect('/auth/login');
    }
    throw error;
  }

  return (
    <>
      <Header />
      {/* ガチャページにゴー */}
      <ClickBtn text="ガチャを引く" url="/gacha" />
      {/* リザルト表示 */}
      <ResultAllCount resultNum={response} />
    </>
  );
}
