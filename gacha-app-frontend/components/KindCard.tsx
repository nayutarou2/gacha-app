'use client';
import styles from '@/components/KindCard.module.css';
import { KindsData } from '@/app/interface/KindsData';
import { pullGacha } from '@/app/api/pull';
import { useRouter } from 'next/navigation';

// propsでそれぞれのコンポーネントから値を受け取る
interface KindProps {
  response: KindsData[];
  token:string
}

export default function KindsCard(props: KindProps) {
  const router = useRouter();

  const onClickKindHandler = async (kindNum: number) => {
    try {
      const response = await pullGacha(kindNum,props.token);
      console.log('レスポンス:', response);
      console.log('result?', JSON.stringify(response.gachaResult));
      localStorage.setItem('gachaResult', JSON.stringify(response.gachaResult));
      router.push(`/gacha/result/${response.id}`);
    } catch (error) {
      console.error('通信失敗 : ', error);
    }
  };

  return (
    <>
      {props.response.map((data) => (
        <div key={data.id}>
          <div className={styles.kind_card} onClick={() => onClickKindHandler(data.kindsNum)}>
            {data.kindsNum}
          </div>
          <div className={styles.shadow}></div>
        </div>
      ))}
    </>
  );
}
