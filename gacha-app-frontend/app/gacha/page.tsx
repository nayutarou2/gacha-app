import ChooseKind from '@/components/ChooseKind';
import Header from '@/components/Header';
import Result from '@/app/gacha/result/[id]/page';
import { allKinds } from '@/app/api/kinds';

export default async function Gacha() {
  const respose = await allKinds();

  return (
    // choose kinds
    <>
      <Header />
      <ChooseKind resposne={respose} />
    </>
  );
}
