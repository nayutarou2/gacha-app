import styles from './result.module.css';
import Title from '@/components/Title';
import ClickBtn from '@/components/ClickBtn';
import { selectByResultId } from '@/app/api/history';
import ResultDetail from '@/components/ResultDetail';
import ResultCard from '@/components/ResultCard';
import { notFound } from 'next/navigation';

export default async function Result({ params }: { params: Promise<{ id: string }> }) {
  // 1. ここで await して中身を取り出す（これが重要！）
  const resolvedParams = await params;
  const resultId = resolvedParams.id;
  // 型変換
  const id = Number(resultId);

  if (Number.isNaN(id)) {
    notFound();
  }

  try {
    // バックエンドに問い合わせる
    const response = await selectByResultId(id);

    // 3. もしデータがなければエラー表示（または404ページへリダイレクト）
    if (!response) {
      notFound();
    }

    const result = [response.scount, response.acount, response.bcount, response.ccount];

    return (
      <>
        <Title text="結果" />
        {/* 結果画面 S A B C を出す */}

        <ResultCard />

        {/* ResultDetail */}
        <ResultDetail resultCount={result} />

        {/*  */}
        <div className={styles.btn_list}>
          <ClickBtn text="もう一度引く" url="/gacha" />
          <ClickBtn text="ホーム画面に戻る" url="/" />
        </div>
      </>
    );
  } catch (error: any) {
    if (error.response?.status === 404) {
      notFound();
    }
    throw new Error("API通信中にエラーが発生しました");
  }
}
