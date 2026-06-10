import styles from '@/components/ResultDetail.module.css';

interface ResultCountList {
  resultCount: number[] | undefined;
}

export default function ResultDetail(props: ResultCountList) {
  const resultsList = props.resultCount || [];

  // reduceを使い、配列の要素を左から右へ順番に処理する
  const totalCount = resultsList.reduce((acc, curr) => acc + curr, 0);

  const textChange = (index: number) => {
    switch (index) {
      case 0:
        return 'S';
      case 1:
        return 'A';
      case 2:
        return 'B';
      case 3:
        return 'C';
      default:
        return '';
    }
  };

  return (
    <>
      <div className={styles.results}>
        {resultsList.map((data, index) => (
          <div key={index}>
            <div className={''}>
              {textChange(index)} : <span>{data}</span>回
            </div>
          </div>
        ))}
        <div>
          合計 : <span>{totalCount}回</span>
        </div>
      </div>
    </>
  );
}
