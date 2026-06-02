import Card from '@/components/KindCard';
import Title from '@/components/Title';
import styles from '@/components/ChooseKind.module.css';
import { allKinds } from '@/app/api/kinds';
import { KindsData } from '@/app/interface/KindsData';
import KindsCard from '@/components/KindCard';

interface KindProps {
  resposne: KindsData[];
}

export default function ChooseKind(props: KindProps) {
  return (
    // choose kinds
    <>
      {/* コンポーネントを作る */}
      {/* ここを大きく囲ってその中でh2の文字を書くコンポーネントとカードのやつを書く */}
      <Title text="引く回数を選択してください" />
      {/* dbから引く種類を取得 */}
      <div className={styles.box}>
        <KindsCard response={props.resposne} />
      </div>
    </>
  );
}
