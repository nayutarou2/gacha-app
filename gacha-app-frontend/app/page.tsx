import Header from '@/components/Header';
import ClickBtn from '@/components/ClickBtn';
import ResultAllCount from '@/components/ResultAllCount';
import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';
import { getAllResult } from './api/history';

export default async function Home() {

  // cookieを取得
  const cookieStore = await cookies();
  // cookieに入っているtokenを取得
  const token = cookieStore.get('jwt_token')?.value;

  // tokenがない場合はログインさせる
  if(!token){
    redirect('/auth/login');
  }

  const resposne = await getAllResult(token);
  console.log("response",resposne);

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
