import styles from '@/components/ResultAllCount.module.css';

interface Props {
  resultNum: number;
}

export default async function ResultAllCount(props: Props) {
  return (
    <>
      <div className={styles.gacha_history}>
        これまでに {props.resultNum}連ガチャが回されています。
      </div>
    </>
  );
}
