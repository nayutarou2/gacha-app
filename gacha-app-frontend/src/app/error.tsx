// app/error.tsx
'use client';

import ClickBtn from '@/src/components/ui/ClickBtn';
import { useEffect } from 'react';

export default function Error({
  error,
  reset,
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) {
  useEffect(() => {
    // ここでエラーをコンソールに出力してデバッグしやすくします
    console.error('システムエラー:', error);
  }, [error]);

  return (
    <>
      <div style={{ textAlign: 'center', margin: '50px' }}>
        <h2>500 - エラーが発生しました</h2>
        <p>申し訳ありません。システムで問題が発生しました。</p>
      </div>
      <div style={{ display: 'flex', justifyContent: 'center', gap: '20px' }}>
        {/* もう一度描画を試みるボタン */}
        <button onClick={() => reset()} style={{ padding: '10px 20px', cursor: 'pointer' }}>
          再読み込み
        </button>
        <ClickBtn text="ホーム画面に戻る" url="/" />
      </div>
    </>
  );
}
