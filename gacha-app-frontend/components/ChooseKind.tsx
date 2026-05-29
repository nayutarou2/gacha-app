import Card from "@/components/Card";
import Title from "@/components/Title";
import styles from "@/components/ChooseKind.module.css"
import { allKinds } from "@/app/api/kinds";

// interface Props{
//   onClickChengeHander :() => void;
// }

export default async function ChooseKind(){

  const kinds = await allKinds();

  return(
       // choose kinds
    <>
      {/* コンポーネントを作る */}
      {/* ここを大きく囲ってその中でh2の文字を書くコンポーネントとカードのやつを書く */}
      <Title text='引く回数を選択してください'/>
      {/* dbから引く種類を取得 */}
      <div className={styles.box}>
        <Card card="kind"/>
      </div>
    </>
  )

}