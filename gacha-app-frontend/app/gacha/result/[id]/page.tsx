import styles from './result.module.css';
import Title from '@/components/Title';
import ClickBtn from '@/components/ClickBtn';
import { selectByResultId } from '@/app/api/history';
import ResultDetail from '@/components/ResultDetail';
import ResultCard from '@/components/ResultCard';
import { notFound, redirect } from 'next/navigation';
import axios from 'axios';
import { cookies } from 'next/headers';

export default async function Result({ params }: { params: Promise<{ id: string }> }) {

  // cookieを取得
  const cookieStore = await cookies();
  // cookieに入っているtokenを取得
  const token = cookieStore.get('jwt_token')?.value;

  // tokenがない場合はログインさせる
  if (!token) {
    redirect('/auth/login');
  }

  // 1. ここで await して中身を取り出す（これが重要！）
  const resolvedParams = await params;
  const resultId = resolvedParams.id;
  // 型変換
  const id = Number(resultId);

  if (Number.isNaN(id)) {
    notFound();
  }

  let response;

  try {
    // バックエンドに問い合わせる
    response = await selectByResultId(id,token);
    console.log("ガチャ結果",response);

    // 3. もしデータがなければエラー表示（または404ページへリダイレクト）
    if (!response) {
      notFound();
    }
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 404) {
      notFound();
    }
    console.log("エラーログ",error);
    throw new Error('API通信中にエラーが発生しました');
  }

  const resultCount = response.gachaResults;
  console.log("resultCount",resultCount);
  const resultDetail = response.gachaResultDetails;
  console.log("resultDetail",resultDetail);

  return (
    <>
      <Title text="結果" />
      {/* 結果画面 S A B C を出す */}

      <ResultCard result={resultDetail} />

      {/* ResultDetail */}
      <ResultDetail resultCount={resultCount}  />

      {/*  */}
      <div className={styles.btn_list}>
        <ClickBtn text="もう一度引く" url="/gacha" />
        <ClickBtn text="ホーム画面に戻る" url="/" />
      </div>
    </>
  );
}
