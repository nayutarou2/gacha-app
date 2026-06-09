import AuthForm from "@/components/AuthForm";
import styles from "./register.module.css";
import { registerAction } from "@/app/api/auth";

export default function Register() {

  const initialState = {
    success: false,
    username: "",
    email: "",
  };

  return (
    <div className={styles.body}>
      <div className={styles.login}>
        <h1 className={styles.h1}>アカウント登録</h1>
        <AuthForm auth="register" action={registerAction} initialState={initialState} />
      </div>
    </div>
  );
}