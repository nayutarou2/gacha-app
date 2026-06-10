import { logoutAction } from '@/src/lib/actions/authActions';
import styles from './Logout.module.css';

export default function Logout() {
  return (
    <form action={logoutAction} className={styles.form}>
      <button type="submit">ログアウト</button>
    </form>
  );
}
