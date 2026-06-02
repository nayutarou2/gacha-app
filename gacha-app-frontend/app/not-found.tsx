import ClickBtn from '@/components/ClickBtn';

export default function NotFound() {
  return (
    <>
      <div style={{ textAlign: 'center', margin: '50px' }}>
        <h2>404 - ページが見つかりませんでした。</h2>
        <p>お探しのガチャ結果は存在しません</p>
      </div>
      <ClickBtn text="ホーム画面に戻る" url="/" />
    </>
  );
}
