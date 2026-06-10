import Title from '@/src/components/ui/Title';
import styles from '@/components/ChooseKind.module.css';
import { KindsData } from '@/src/types/kinds';
import KindsCard from '@/src/components/gacha/KindCard';

interface KindProps {
  response: KindsData[];
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
        <KindsCard response={props.response} />
      </div>
    </>
  );
}
