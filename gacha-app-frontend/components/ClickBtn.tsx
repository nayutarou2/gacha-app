'use client';

import styles from './ClicBtn.module.css';
import { useRouter } from 'next/navigation';

interface TextProps {
  text: string;
  url: string;
}

export default function ClickBtn(props: TextProps) {
  const router = useRouter();

  return (
    <button type="button" className={styles.pull} onClick={() => router.push(props.url)}>
      {props.text}
    </button>
  );
}
