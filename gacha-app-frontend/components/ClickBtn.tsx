'use client';

import { url } from 'inspector';
import styles from './ClicBtn.module.css';
import { useRouter } from 'next/navigation';

interface TextProps{
  text:string;
  url : string;
}

export default function ClickBtn(props:TextProps) {
  const router = useRouter();

  return (
    <div className={styles.pull} onClick={() => router.push(props.url)}>
      {props.text}
    </div>
  );
}
