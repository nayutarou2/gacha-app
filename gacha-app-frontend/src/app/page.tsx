import Header from '@/src/components/layout/Header';
import ClickBtn from '@/src/components/ui/ClickBtn';
import ResultAllCount from '@/src/components/gacha/ResultAllCount';
import { getAllResult } from '@/src/lib/api/history';
import axios from 'axios';
import { redirect } from 'next/navigation';

export default async function Home() {
  let response;

  try {
    response = await getAllResult();
  } catch (error) {
    if (axios.isAxiosError(error) && (error.response?.status === 401 || error.response?.status === 403)) {
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
