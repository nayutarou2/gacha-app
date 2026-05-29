"use client"
import { useEffect, useState } from 'react';
import styles from './Card.module.css';
import { KindsData } from '@/app/interface/KindsData';
import { ResultData } from '@/app/interface/ResultData';
import { allKinds } from '@/app/api/kinds';
import { pullGacha } from '@/app/api/pull';
import { useRouter } from 'next/navigation';
import ResultDetail from './ResultDetail';

// propsでそれぞれのコンポーネントから値を受け取る
interface CardProps {
  card: string;
}

export default function Card(props: CardProps) {
  /**
   * ページごとに分けていて、リザルト画面とガチャの種類を選ぶ画面の違いは
   * 適用するcssとdivが一つ多いだけ
   * propsで値を受け取ってそれによって適用するcssを変えるかdivタグを表示するか
   * 切り替える感じでいいはず
   */

  const [card, setCard] = useState('');
  const [kinds, setKinds] = useState<KindsData[]>([]);
  const [results, setResults] = useState<ResultData | null>(null);
  // const [resultCounts,setResultsCounts] = useState<ResultData>();

  const router = useRouter();

  useEffect(() => {
    const getCardStyle = async () => {
      if (props.card === 'kind') {
        setCard('kind');
        try {
          const respose = await allKinds();
          setKinds(respose);
        } catch (error) {
          console.error('取得に失敗 : ', error);
        }
        getStyle(card);
      } else if (props.card === 'result') {
        setCard('result');
        try {

        } catch (error) {
          console.error('取得に失敗 : ', error);
        }
        getStyle(card);
      }
    };
    getCardStyle();
    const resultsList = sessionStorage.getItem("gachaResult");
    // const resultCountList = sessionStorage.getItem("gachaResultDetail");
    console.log(resultsList);
    if (resultsList) {
      setResults(JSON.parse(resultsList));
    } else {
      console.log("取得できませんでした");
    }
  }, []);

  const getStyle = (card: string) => {
    if (card === 'kind') {
      return styles.kind_card;
    } else if (card === 'result') {
      return styles.result_card;
    }
  };

  /**
   * propsの切り替えによってDBから値を取得するのを切り替える
   */

  const onClickKindHandler = async (kindNum: number) => {

    console.log("クリック");
    try {
      const response = await pullGacha(kindNum);
      console.log("レスポンス:", response);
      sessionStorage.setItem("gachaResult", JSON.stringify(response));
      router.push("/gacha/result");
    } catch (error) {
      console.error("通信失敗 : ", error);
    }
  }

  return (
    <>
      {card === 'kind' &&
        kinds.map((data) => (
          <div key={data.id}>
            <div className={getStyle(card)} onClick={() => onClickKindHandler(data.kindsNum)} >{data.kindsNum}</div>
            <div className={styles.shadow}></div>
          </div>
        ))}
      {card === 'result' &&
        <div className={styles.result}>
          {results?.gachaResult?.map((data, index) => (
            <div key={index} className={styles.margin}>
              <div className={getStyle(card)}>{data}</div>
            </div>
          ))}
        </div>
      }
      {card === 'result' &&
        <ResultDetail resultCount={results?.gachaResultDetail} />
      }
    </>
  );
}
