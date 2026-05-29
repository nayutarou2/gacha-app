import { getAllResult } from "@/app/api/history";
import styles from "@/components/ResultAllCount.module.css"


export default async function ResultAllCount() {

  const response = await getAllResult();

  return (
    <>
      <div className={styles.gacha_history}>これまでに {response}連ガチャが回されています。</div>
    </>
  )


}