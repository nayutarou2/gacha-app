import Card from '@/components/Card';
import styles from './result.module.css';
import Title from '@/components/Title';
import ClickBtn from '@/components/ClickBtn';
// import ResultDetail from '@/components/ResultDetail';

export default function Result() {
  return (
    <>
      <Title text='結果' />
      {/* 結果画面 S A B C を出す */}
      <div>
        <Card card="result" />
      </div>
      {/*  */}
      <div className={styles.btn_list}>
        <ClickBtn text='もう一度引く' url='/gacha'/>
        <ClickBtn text='ホーム画面に戻る' url='/' />
      </div>
    </>
  );
}
