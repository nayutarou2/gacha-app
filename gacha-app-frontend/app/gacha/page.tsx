import ChooseKind from '@/components/ChooseKind';
import Header from '@/components/Header';
import { allKinds } from '@/app/api/kinds';
import { redirect } from 'next/navigation';
import axios from 'axios';

export default async function Gacha() {

  let response

  try {
    response = await allKinds();
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 401) {
      redirect('/auth/login');
    }
    throw error;
  }


  return (
    // choose kinds
    <>
      <Header />
      <ChooseKind resposne={response} />
    </>
  );
}
