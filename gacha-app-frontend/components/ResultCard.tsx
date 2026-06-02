"use client"
import styles from "@/components/ResultCard.module.css";
import { useEffect, useState } from "react";

export default function ResultCard() {

  const [results, setResult] = useState<string[]>([]);

  useEffect(() => {
    setResult(JSON.parse(localStorage.getItem("gachaResult") || "{}") || null);
  }, [])

  if (results[0] === undefined) {
    return <div className={styles.undefined}>詳細を表示できません</div>
  }

  return (
    <>
      <div className={styles.box}>
        <div className={styles.center}>
          {results.map((data, index) => (
            <div key={index} className={styles.item} >
              <div className={styles.result_card} >
                {data}
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );

}