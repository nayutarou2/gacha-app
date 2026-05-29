import Header from '@/components/Header';
import ClickBtn from '@/components/ClickBtn';
import ResultDetail from '@/components/ResultAllCount';
import ResultAllCount from '@/components/ResultAllCount';

export default function Home() {
  return (
    <>
      <Header />
      {/* ガチャページにゴー */}
      <ClickBtn text='ガチャを引く' url='/gacha' />
      {/* リザルト表示 */}
      <ResultAllCount />
    </>
  );
}
