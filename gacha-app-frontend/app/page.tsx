import Header from '@/components/Header';
import ClickBtn from '@/components/ClickBtn';
import ResultAllCount from '@/components/ResultAllCount';
import { getAllResult } from './api/history';

export default async function Home() {

  const resposne = await getAllResult();

  return (
    <>
      <Header />
      {/* ガチャページにゴー */}
      <ClickBtn text="ガチャを引く" url="/gacha" />
      {/* リザルト表示 */}
      <ResultAllCount resultNum={resposne} />
    </>
  );
}
