import { logoutAction } from "@/app/api/auth";
import styles from "./Logout.module.css";


export default function Logout() {

  return (
    <form action={logoutAction} className={styles.form}>
      <button type="submit">
        ログアウト
      </button>
    </form>
  );
}