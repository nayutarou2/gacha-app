'use client';
import styles from '@/components/KindCard.module.css';
import { KindsData } from '@/src/types/kinds';
import { useRouter } from 'next/navigation';
import { startTransition } from 'react';
import { pullGacha } from '@/src/lib/api/pull';

// propsでそれぞれのコンポーネントから値を受け取る
interface KindProps {
  response: KindsData[];
}

export default function KindsCard(props: KindProps) {
  const router = useRouter();

  const onClickKindHandler = async (kindNum: number) => {
    startTransition(async () => {
      const result = await pullGacha(kindNum);
      if (result && result.id) {
        router.push(`/gacha/result/${result.id}`);
      }else if(result.error === 'auth_error'){
        alert('セッションの有効期限が切れました。再度ログインしてください');
        router.push('/auth/login');
      }else{
        alert(result.error);
      }
    })
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
