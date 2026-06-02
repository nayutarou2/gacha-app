'use client';
import styles from '@/components/ResultCard.module.css';
import { useSyncExternalStore } from 'react';

// クライアント側（ブラウザ）でlocalStorageの変更を監視する関数
const subscribe = () => () => {}; // localStorageはリアルタイム同期しないので空の関数でOK

// ブラウザ環境での値の取得方法
const getSnapshot = () => localStorage.getItem('gachaResult');

// サーバーサイド（SSR）環境での初期値（ハイドレーションエラー防止）
const getServerSnapshot = () => null;

export default function ResultCard() {
  // useEffectを使わずに、安全にlocalStorageの値をリアクティブに取得
  const saved = useSyncExternalStore(subscribe, getSnapshot, getServerSnapshot);

  let results: string[] = [];
  if (saved) {
    try {
      results = JSON.parse(saved);
    } catch (e) {
      console.error(e);
    }
  }

  if (results[0] === undefined) {
    return <div className={styles.undefined}>詳細を表示できません</div>;
  }

  return (
    <>
      <div className={styles.box}>
        <div className={styles.center}>
          {results.map((data, index) => (
            <div key={index} className={styles.item}>
              <div className={styles.result_card}>{data}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
