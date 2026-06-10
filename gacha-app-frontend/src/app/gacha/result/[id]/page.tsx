import styles from './result.module.css';
import Title from '@/src/components/ui/Title';
import ClickBtn from '@/src/components/ui/ClickBtn';
import { selectByResultId } from '@/src/lib/api/history';
import ResultDetail from '@/src/components/gacha/ResultDetail';
import ResultCard from '@/src/components/gacha/ResultCard';
import { notFound, redirect } from 'next/navigation';
import axios from 'axios';

export default async function Result({ params }: { params: Promise<{ id: string }> }) {
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
    response = await selectByResultId(id);

    // 3. もしデータがなければエラー表示（または404ページへリダイレクト）
    if (!response) {
      notFound();
    }
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 401) {
      redirect('/auth/login');
    }

    if (axios.isAxiosError(error) && error.response?.status === 404) {
      notFound();
    }
    throw new Error('API通信中にエラーが発生しました');
  }

  const resultCount = response.gachaResults;
  const resultDetail = response.gachaResultDetails;

  return (
    <>
      <Title text="結果" />
      {/* 結果画面 S A B C を出す */}

      <ResultCard result={resultDetail} />

      {/* ResultDetail */}
      <ResultDetail resultCount={resultCount} />

      {/*  */}
      <div className={styles.btn_list}>
        <ClickBtn text="もう一度引く" url="/gacha" />
        <ClickBtn text="ホーム画面に戻る" url="/" />
      </div>
    </>
  );
}
