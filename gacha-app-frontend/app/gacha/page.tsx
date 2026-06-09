import ChooseKind from '@/components/ChooseKind';
import Header from '@/components/Header';
import { allKinds } from '@/app/api/kinds';
import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';

export default async function Gacha() {

  const response = await allKinds();


  return (
    // choose kinds
    <>
      <Header />
      <ChooseKind resposne={response} />
    </>
  );
}
