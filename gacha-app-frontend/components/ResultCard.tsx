'use client';
import styles from '@/components/ResultCard.module.css';

type ResultDetailData = {
  id: number;
  gachaResultId: number;
  turns: number;
  rank: string;
}

interface Props {
  result: ResultDetailData[];
}

export default function ResultCard(props:Props) {



  if (props === null) {
    return <div className={styles.undefined}>詳細を表示できません</div>;
  }

  return (
    <>
      <div className={styles.box}>
        <div className={styles.center}>
          {props.result.map((data) => (
            <div key={data.turns} className={styles.item}>
              <div className={styles.result_card}>{data.rank}</div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
