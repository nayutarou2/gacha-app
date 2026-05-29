"use client"
import styles from "@/components/ResultDetail.module.css";
import { ResultData } from "@/app/interface/ResultData";
import { useState } from "react";

interface ResultCountList {
  resultCount: number[] | undefined;
}

type Count = {
  data:number[];
}

export default function ResultDetail(props: ResultCountList) {

  const [results, setResults] = useState<ResultData | null>(null);

  const resultsList = props.resultCount;
  console.log("reusltsList:",resultsList);

  let count = 0;
  
  const textChange = (index:number,data:number) =>{
    count += data;
    let text = "";
    switch (index) {
      case 0: return text = "S";
      case 1: return text = "A";
      case 2: return text = "B";
      case 3: return text = "C";
      default: return text;
    }
  }


  return (
    <div className={styles.result_count}>
      {
        resultsList?.map((data,index) => (
          <div key={index}>
            <div className={styles.shadow}> {textChange(index,data)} : <span>{data}</span>回</div>
          </div>
        ))
      }
      <div>
        合計 : <span>{count}回</span>
      </div>
    </div>
  )


}