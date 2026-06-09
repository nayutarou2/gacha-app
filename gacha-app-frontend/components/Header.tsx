import Link from 'next/link';
import styles from './Header.module.css';
import Logout from './Logout';

export default function Header() {
  return (
    <header>
      <div className={styles.header}>
        <h1>
          <Link href={'/'}>ガチャApp</Link>
        </h1>
        <div className={styles.auth} >
          <div>ようこそ</div>
          <Logout />
        </div>
      </div>
    </header>
  );
}
